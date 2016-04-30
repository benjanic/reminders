package demo.benjanic.com.common;

import com.google.android.gms.wearable.DataMap;

import java.util.Date;

/**
 * Created by Ben on 22/04/2016.
 */
public class Reminder {
    private String title = "";
    private String body = "";
    private Date date;
    final public static String EXTRA_SAVE_KEY = "save_key";

    public Reminder(String title, String body, Date date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public Reminder(String saveData) {
        String[] saveDataArray = saveData.split("~");
        setTitle(saveDataArray[0].toString());
        setBody(saveDataArray[1].toString());

        Date date = new Date(Long.parseLong(saveDataArray[2].toString()));
        setDate(date);
    }

    public Reminder(DataMap dataMap) {
        setTitle(dataMap.getString("title"));
        setBody(dataMap.getString("body"));
        setDate(new Date(dataMap.getLong("date")));
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the string that will be saved into the app
     *
     * @return
     */
    public String getSaveString() {
        String saveString = getTitle() + "~" + getBody() + "~" + String.valueOf(getDate().getTime());
        return saveString;
    }

    public DataMap getDataMap() {
        DataMap dataMap = new DataMap();
        dataMap.putString("title", getTitle());
        dataMap.putString("body", getBody());
        dataMap.putLong("date", getDate().getTime());

        return dataMap;
    }

    @Override
    public String toString() {
        return getTitle() + " " + getBody() + " " + getDate();
    }
}
