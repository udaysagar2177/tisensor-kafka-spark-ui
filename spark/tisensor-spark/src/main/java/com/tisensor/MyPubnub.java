package com.tisensor;

import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.*;

/**
 * Created by uday on 5/15/16.
 */
public final class MyPubnub {
    static final Pubnub pubnub = new Pubnub("pub-c-22533423-da0e-4038-aef9-31a1dc2a58c", "sub-c-e7aa427c-1b1a-11e6-9a17-0619f8945a4");
    static final Callback callback = new Callback() {
        public void successCallback(String channel, Object response) {
            System.out.println(response.toString());
        }
        public void errorCallback(String channel, PubnubError error) {
            System.out.println(error.toString());
        }
    };
}
