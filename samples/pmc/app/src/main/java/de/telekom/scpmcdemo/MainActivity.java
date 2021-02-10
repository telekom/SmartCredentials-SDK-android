package de.telekom.scpmcdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import de.telekom.smartcredentials.core.api.PolicyApi;
import de.telekom.smartcredentials.core.pmc.PoliciesCallback;
import de.telekom.smartcredentials.pmc.PolicyController;
import de.telekom.smartcredentials.pmc.PolicySchemaResponse;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PolicyApi<PolicySchemaResponse> policyController = new PolicyController();
        policyController.fetchPolicies(new PoliciesCallback<PolicySchemaResponse>() {
            @Override
            public void onSuccess(PolicySchemaResponse policies) {
                Timber.tag(DemoApplication.TAG).d(policies.getOdataContext());
                Timber.tag(DemoApplication.TAG).d("policies size: %s", policies.getValue().size());
            }

            @Override
            public void onFailure() {
                Timber.tag(DemoApplication.TAG).d("onFailure");
            }
        });
    }
}
