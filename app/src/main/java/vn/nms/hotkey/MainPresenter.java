package vn.nms.hotkey;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MainPresenter {
    private MainView view;

    MainPresenter() {
        String x = formatString("nguyễn nhật ánh");
    }

    @SuppressLint("StaticFieldLeak")
    void loadHoyKey() {
        view.onShowLoading();
        new AsyncTask<Void, List<String>, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(Constants.GET_HOT_KEY_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    ArrayList<String> result = new ArrayList<>();
                    JSONArray jArray = new JSONArray(sb.toString());
                    for (int i = 0; i < jArray.length(); i++) {
                        result.add(jArray.getString(i));
                    }
                    return result;
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<String> results) {
                super.onPostExecute(results);
                if (view != null) {
                    view.onHideLoading();
                    if (results != null) {
                        List<HotKeyWordModel> data = new ArrayList<>();
                        for (String item : results) {
                            data.add(new HotKeyWordModel(formatString(item), getRandomColor()));
                        }
                        view.onGetHoyKeySuccess(data);
                    } else {
                        view.onGetHotKeyFail();
                    }
                }
            }
        }.execute();

    }

    void attachView(MainView view) {
        this.view = view;
    }

    void detachView() {
        this.view = null;
    }

    private String formatString(String input) {
        if (input != null) {
            String[] array = input.split(" ");
            int limitCharPerLine = (input.length() - array.length - 1) / 2;
            if (array.length > 2) {
                StringBuilder sb = new StringBuilder();
                boolean isBreakLine = false;
                int currentLength = 0;
                for (int i = 0; i < array.length; i++) {
                    currentLength += array[i].length();
                    if (i != 0 && currentLength > limitCharPerLine && !isBreakLine) {
                        sb.append("\n");
                        isBreakLine = true;
                    }
                    sb.append(array[i]);
                    if (i < array.length - 1) {
                        sb.append(" ");
                    }
                }
                return sb.toString();
            }
        }
        return input;
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(200, rnd.nextInt(200), rnd.nextInt(200),
                rnd.nextInt(200));
    }

}
