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
            int center = input.length() % 2 == 0 ? input.length() / 2 : input.length() / 2 + 1;
            int breakLineIndex = -1;
            if (input.charAt(center) == ' ') {
                breakLineIndex = center;
            } else {
                for (int i = 1; i < center - 1; i++) {
                    if (input.charAt(center - i) == ' ') {
                        breakLineIndex = center - i;
                        break;
                    } else if (input.charAt(center + i) == ' ') {
                        breakLineIndex = center + i;
                        break;
                    }
                }
            }
            if (breakLineIndex != -1) {
                return input.substring(0, breakLineIndex).trim() + "\n"
                        + input.substring(breakLineIndex + 1, input.length()).trim();
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
