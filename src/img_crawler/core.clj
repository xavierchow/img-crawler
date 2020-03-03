(ns img-crawler.core
  (:require [clj-http.client :as client]
            [clojure.java.io :as io]
            [clojure.string :refer [starts-with?]])
  (:gen-class))

(defn fetch-html
  [url]
  (:body (client/get url)))

(defn build-photo
  [urls]
  (map-indexed (fn [idx url] {:id idx :url url}) urls))

(defn fetch-photo!
  "makes an HTTP request and fetches the binary object"
  [url]
  (let [req (client/get url {:as :byte-array :throw-exceptions false})]
    (if (= (:status req) 200)
      (:body req))))

(defn save-photo!
  "downloads and stores the photo on disk"
  [photo]
  (let [p (fetch-photo! (:url photo)) path (str "photos-" (:id photo) ".jpeg")]
    (if (not (nil? p))
      (with-open [w (io/output-stream path)]
        (do
          (.write w p)
          (println (str path " saved")))))))

(defn get-gallery
  [html]
  (->> (re-seq #"data-src=\"([^\"]*)\"" html)
       (map last)
       (filter #(starts-with? % "https://mmbiz"))
       (build-photo)
       (map save-photo!)))

(defn -main
  [url]
  ;; url: "https://mp.weixin.qq.com/s/YpMnJ5_625zOq_huX8e83g"
  (dorun(get-gallery (fetch-html url))))
