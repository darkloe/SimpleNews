package database.NewsDbSchema;

public class NewsDbSchema {
    public static final class NewsTable {
        public static final String NAME = "news";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String SOURCE = "source";
            public static final String DES = "description";
            public static final String URL = "url";
        }
    }
}
