package de.telekom.smartcredentials.pushnotifications.rx;

import org.jetbrains.annotations.NotNull;

import de.telekom.smartcredentials.pushnotifications.controllers.PushNotificationsController;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class TokenRetrievedSingle implements SingleOnSubscribe<String> {

    private final PushNotificationsController mController;

    public TokenRetrievedSingle(PushNotificationsController controller) {
        mController = controller;
    }

    @Override
    public void subscribe(@NotNull SingleEmitter<String> emitter) {
        mController.retrieveToken(emitter::onSuccess);
    }
}