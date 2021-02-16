package de.telekom.scpmcdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.pmc.PoliciesCallback;
import de.telekom.smartcredentials.pmc.Policy;
import de.telekom.smartcredentials.pmc.factory.SmartCredentialsPmcFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private PoliciesAdapter adapter;
    private List<Policy> policies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        policies = new ArrayList<>();
        adapter = new PoliciesAdapter(policies);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SmartCredentialsPmcFactory.getPolicyApi().fetchPolicies("contains(PolicySchemaContent,'org:sc:policy:') eq true",
                new PoliciesCallback<List<Policy>>() {
                    @Override
                    public void onSuccess(List<Policy> response) {
                        Timber.tag(DemoApplication.TAG).d(response.toString());
                        policies.clear();
                        policies.addAll(response);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Timber.tag(DemoApplication.TAG).d("onFailure: %s", t.toString());
                    }
                });
    }
}
