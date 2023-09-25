// IOperatorTokenCallback.aidl
package de.telekom.smartcredentials.core;

interface IOperatorTokenCallback {

    void onOperatorTokenReceived(boolean isSuccess, String operatorToken, String errorMessage);
}