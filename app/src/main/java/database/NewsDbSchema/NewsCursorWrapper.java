package database.NewsDbSchema;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.allenanker.android.simplenews.News;

import java.util.UUID;

import database.NewsDbSchema.NewsDbSchema.NewsTable;

public class NewsCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public NewsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public News getNews() {

        String uuidString = getString(getColumnIndex(NewsTable.Cols.UUID));
        String title = getString(getColumnIndex(NewsTable.Cols.TITLE));
        String source = getString(getColumnIndex(NewsTable.Cols.SOURCE));
        String description = getString(getColumnIndex(NewsTable.Cols.DES));
        String url = getString(getColumnIndex(NewsTable.Cols.URL));

        News news = new News(UUID.fromString(uuidString));
        news.setTitle(title);
        news.setSource(source);
        news.setDes(description);
        news.setUrl(url);

        return news;
    }
}
