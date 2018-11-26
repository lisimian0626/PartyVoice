package pl.sugl.common.annotation;

public enum ViewState {
    NETWORK_ERROR("network"),
    LOADING("loading");

    ViewState(String key) {
        mKey = key;
    }

    private final String mKey;

    public ViewState getViewState(String key) {
        ViewState state;
        switch (key) {
            case "network":
                state = NETWORK_ERROR;
                break;
            default:
                state = LOADING;
                break;
        }

        return state;
    }
}
