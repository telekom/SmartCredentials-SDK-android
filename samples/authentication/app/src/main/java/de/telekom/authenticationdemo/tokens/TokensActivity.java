package de.telekom.authenticationdemo.tokens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.telekom.authenticationdemo.IdentityProvider;
import de.telekom.authenticationdemo.R;
import de.telekom.authenticationdemo.login.LoginActivity;
import de.telekom.authenticationdemo.profile.ProfileActivity;
import de.telekom.authenticationdemo.task.SmartTask;
import de.telekom.smartcredentials.authentication.AuthStateManager;
import de.telekom.smartcredentials.authentication.factory.SmartCredentialsAuthenticationFactory;
import de.telekom.smartcredentials.core.api.AuthenticationApi;
import de.telekom.smartcredentials.core.authentication.AuthenticationError;
import de.telekom.smartcredentials.core.authentication.AuthenticationTokenResponse;
import de.telekom.smartcredentials.core.authentication.AuthorizationException;
import de.telekom.smartcredentials.core.authentication.OnFreshTokensRetrievedListener;
import de.telekom.smartcredentials.core.authentication.TokenRefreshListener;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

public class TokensActivity extends AppCompatActivity {

    private AuthenticationApi authenticationApi;
    private TokensAdapter adapter;
    private List<Token> tokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tokens);

        authenticationApi = SmartCredentialsAuthenticationFactory.getAuthenticationApi();

        RecyclerView recyclerView = findViewById(R.id.tokens_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        tokens = new ArrayList<>();
        adapter = new TokensAdapter(this, tokens);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchTokens();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tokens, menu);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_profile_item:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case R.id.perform_action_item:
                SmartTask.with(this)
                        .assign(() -> authenticationApi.performActionWithFreshTokens(new OnFreshTokensRetrievedListener() {
                            @Override
                            public void onRefreshComplete(@Nullable String accessToken, @Nullable String idToken, @Nullable AuthorizationException exception) {
                                runOnUiThread(() -> {
                                    Toast.makeText(TokensActivity.this, R.string.perform_action_success,
                                            Toast.LENGTH_SHORT).show();
                                    fetchTokens();
                                });
                            }

                            @Override
                            public void onFailed(AuthenticationError errorDescription) {
                                runOnUiThread(() -> Toast.makeText(TokensActivity.this,
                                        R.string.perform_action_failed, Toast.LENGTH_SHORT).show());
                            }
                        }))
                        .execute();
                return true;
            case R.id.refresh_access_item:
                SmartTask.with(this)
                        .assign(() -> authenticationApi.refreshAccessToken(new TokenRefreshListener<AuthenticationTokenResponse>() {
                            @Override
                            public void onTokenRequestCompleted(@Nullable AuthenticationTokenResponse response, @Nullable AuthorizationException exception) {
                                runOnUiThread(() -> {
                                    fetchTokens();
                                    Toast.makeText(TokensActivity.this, R.string.refresh_access_token_success,
                                            Toast.LENGTH_SHORT).show();
                                });
                            }

                            @Override
                            public void onFailed(AuthenticationError errorDescription) {
                                runOnUiThread(() -> Toast.makeText(TokensActivity.this,
                                        R.string.refresh_access_token_failed, Toast.LENGTH_SHORT).show());
                            }
                        }))
                        .execute();
                return true;
            case R.id.logout_item:
                LogoutDialogFragment logoutDialogFragment = new LogoutDialogFragment();
                logoutDialogFragment.show(getSupportFragmentManager(), LogoutDialogFragment.TAG);
                SmartTask.with(this)
                        .assign(() -> authenticationApi.logOut())
                        .finish(result -> {
                            SmartCredentialsApiResponse<Boolean> response = (SmartCredentialsApiResponse<Boolean>) result;
                            logoutDialogFragment.dismiss();
                            if (response != null && response.isSuccessful() && response.getData()) {
                                startActivity(new Intent(this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(this, R.string.logout_failed, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("unchecked")
    private void fetchTokens() {
        SmartTask.with(this)
                .assign(() -> {
                    AuthStateManager authStateManager = AuthStateManager.getInstance(this, IdentityProvider.GOOGLE.getName());
                    List<Token> tokenList = new ArrayList<>();
                    tokenList.add(new Token(R.string.access_token, authStateManager.getAccessToken(), authStateManager.getAccessTokenExpirationTime()));
                    tokenList.add(new Token(R.string.id_token, authStateManager.getIdToken(), Token.DEFAULT_VALIDITY));
                    tokenList.add(new Token(R.string.refresh_token, authStateManager.getRefreshToken(), Token.DEFAULT_VALIDITY));
                    return tokenList;
                })
                .finish(result -> {
                    if (result != null) {
                        List<Token> tokenList = (List<Token>) result;
                        tokens.clear();
                        tokens.addAll(tokenList);
                        adapter.notifyDataSetChanged();
                    }
                })
                .execute();
    }
}