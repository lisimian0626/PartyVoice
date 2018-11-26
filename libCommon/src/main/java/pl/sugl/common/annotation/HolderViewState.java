package pl.sugl.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: Hanson
 * date:   2017/7/29
 * describe:
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HolderViewState {
    /**
     * 需要处理的状态
     * @return
     */
    ViewState[] handleStates() default {ViewState.NETWORK_ERROR, ViewState.LOADING};

    /**
     * 状态view的宿主view的identifyName
     * @return
     */
    String holderIdentifyName() default "";

    /**
     * 状态显示时，需要隐藏Gone掉的view identifyName json格式{"network":[], "loading":[] }
     * @see ViewState
     * @return
     */
    String[] goneViewIdentifyName() default {"{}"};

    boolean showNetworkOffPrompt() default false;
}
