package zkch.com.framework.okhttp.bean;

import java.util.List;

public class DataBean {
    /**
     * id : 61684
     * movieName : 《猜火车2》先导预告片
     * coverImg : http://img31.mtime.cn/mg/2016/07/26/143142.64770465.jpg
     * movieId : 228230
     * url : http://vfx.mtime.cn/Video/2016/07/26/mp4/160726074707321432_480.mp4
     * hightUrl : http://vfx.mtime.cn/Video/2016/07/26/mp4/160726074707321432.mp4
     * videoTitle : 猜火车2 先导预告片
     * videoLength : 46
     * rating : -1 , 0 , 1
     * type : ["剧情"]
     * summary : 苏格兰四兄弟回来了！
     */

    private List<ItemData> trailers;

    @Override
    public String toString() {
        return "DataBean{" +
                "trailers=" + trailers +
                '}';
    }

    public void setTrailers(List<ItemData> trailers) {
        this.trailers = trailers;
    }

    public List<ItemData> getTrailers() {
        return trailers;
    }

    public static class ItemData {
        private int id;
        private String movieName;
        private String coverImg;
        private int movieId;
        private String url;
        private String hightUrl;
        private String videoTitle;
        private int videoLength;
        private String rating;
        private String summary;
        private List<String> type;

        @Override
        public String toString() {
            return "ItemData{" +
                    "id=" + id +
                    ", movieName='" + movieName + '\'' +
                    ", coverImg='" + coverImg + '\'' +
                    ", movieId=" + movieId +
                    ", url='" + url + '\'' +
                    ", hightUrl='" + hightUrl + '\'' +
                    ", videoTitle='" + videoTitle + '\'' +
                    ", videoLength=" + videoLength +
                    ", rating=" + rating +
                    ", summary='" + summary + '\'' +
                    ", type=" + type +
                    '}';
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setHightUrl(String hightUrl) {
            this.hightUrl = hightUrl;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public void setVideoLength(int videoLength) {
            this.videoLength = videoLength;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public void setType(List<String> type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public String getMovieName() {
            return movieName;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public int getMovieId() {
            return movieId;
        }

        public String getUrl() {
            return url;
        }

        public String getHightUrl() {
            return hightUrl;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public int getVideoLength() {
            return videoLength;
        }

        public String getRating() {
            return rating;
        }

        public String getSummary() {
            return summary;
        }

        public List<String> getType() {
            return type;
        }
    }
}
