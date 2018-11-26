package pl.sugl.common.annotation;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HolderViewStateHelper {
    public static StateEntity parseHolderViewState(Context context, ViewGroup container, Class<?> clazz) {
        StateEntity stateEntity = new StateEntity();

        HolderViewState viewState = clazz.getAnnotation(HolderViewState.class);
        if (viewState != null) {
            String holderIdentifyName = viewState.holderIdentifyName();
            if (!TextUtils.isEmpty(holderIdentifyName)) {
                stateEntity = new StateEntity();

                int id = context.getResources().getIdentifier(holderIdentifyName, "id", context.getPackageName());
                stateEntity.setHolderId(id);
                View view = container.findViewById(id);
                stateEntity.setHolderView(view);
                stateEntity.setHolderViewIndex(container.indexOfChild(view));
                stateEntity.setHolderLayoutParams(view.getLayoutParams());
                parseParams(context, stateEntity, viewState);
            }
        }

        return stateEntity;
    }

    private static void parseParams(Context context, StateEntity stateEntity, HolderViewState viewState) {

        try {
            String[] goneViews = viewState.goneViewIdentifyName();
            for (String item : goneViews) {
                JsonParser jsonParser = new JsonParser();
                JsonObject object = jsonParser.parse(item).getAsJsonObject();
                int[] networkGoneIds = parseParamsByName(context, object, ViewState.NETWORK_ERROR.name());
                if (networkGoneIds != null) {
                    stateEntity.getGoneViewMap().put(ViewState.NETWORK_ERROR, networkGoneIds);
                }
                int[] loadingGoneIds = parseParamsByName(context, object, ViewState.LOADING.name());
                if (loadingGoneIds != null) {
                    stateEntity.getGoneViewMap().put(ViewState.NETWORK_ERROR, loadingGoneIds);
                }
            }
        } catch (Exception e) {
            Log.e("HolderViewStateHelper", "parseHolderViewState: ");
        }
    }

    private static int[] parseParamsByName(Context context, JsonObject object, String name) {
        int[] ids = null;
        if (object.has(name)) {
            JsonArray array = object.getAsJsonArray(name);
            if (array != null && array.size() > 0) {
                ids = new int[array.size()];
                for (int i = 0; i < array.size(); i++) {
                    ids[i] = context.getResources().getIdentifier(array.get(i).getAsString(), "id", context.getPackageName());
                }

            }
        }

        return ids;
    }
}
