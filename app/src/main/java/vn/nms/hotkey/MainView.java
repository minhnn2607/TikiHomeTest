package vn.nms.hotkey;

import java.util.List;

interface MainView {
    void onGetHoyKeySuccess(List<HotKeyWordModel> results);

    void onGetHotKeyFail();

    void onShowLoading();

    void onHideLoading();
}
