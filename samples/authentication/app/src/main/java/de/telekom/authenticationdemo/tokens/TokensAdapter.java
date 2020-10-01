package de.telekom.authenticationdemo.tokens;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.PlainJWT;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.telekom.authenticationdemo.R;


/**
 * Created by Alex.Graur@endava.com at 9/1/2020
 */
public class TokensAdapter extends RecyclerView.Adapter<TokensAdapter.ViewHolder> {

    private final List<Token> tokens;

    public TokensAdapter(List<Token> tokens) {
        this.tokens = tokens;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_token,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(tokens.get(position));
    }

    @Override
    public int getItemCount() {
        return tokens.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tokenLabelTextView;
        private final TextView tokenTypeLabelTextView;
        private final TextView tokenTypeValueTextView;
        private final TextView tokenValueTextView;
        private final TextView expiresTextView;
        private final TextView tokenValidityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tokenLabelTextView = itemView.findViewById(R.id.token_label_text_view);
            tokenTypeLabelTextView = itemView.findViewById(R.id.token_type_label_text_view);
            tokenTypeValueTextView = itemView.findViewById(R.id.token_type_value_text_view);
            tokenValueTextView = itemView.findViewById(R.id.token_value_text_view);
            expiresTextView = itemView.findViewById(R.id.expires_text_view);
            tokenValidityTextView = itemView.findViewById(R.id.token_validity_text_view);
        }

        public void bind(@NonNull Token token) {
            tokenLabelTextView.setText(token.getTokenType().getNameResId());

            if (token.getTokenType() == TokenType.ID_TOKEN) {
                tokenTypeLabelTextView.setVisibility(View.VISIBLE);
                tokenTypeValueTextView.setVisibility(View.VISIBLE);
                try {
                    JWT idToken = JWTParser.parse(token.getToken());

                    if (idToken instanceof SignedJWT) {
                        tokenTypeValueTextView.setText(R.string.signed_jwt);
                        SignedJWT jwt = (SignedJWT) idToken;
                        Payload payload = jwt.getPayload();
                        tokenValueTextView.setText(payload.toString());
                    } else if (idToken instanceof PlainJWT) {
                        tokenTypeValueTextView.setText(R.string.plain_jwt);
                        PlainJWT jwt = (PlainJWT) idToken;
                        Payload payload = jwt.getPayload();
                        tokenValueTextView.setText(payload.toString());
                    } else {
                        tokenTypeValueTextView.setText(R.string.unknown);
                        tokenValueTextView.setText(token.getToken());
                    }
                } catch (ParseException e) {
                    tokenValueTextView.setText(R.string.no_token_value);
                }

            } else {
                tokenTypeLabelTextView.setVisibility(View.GONE);
                tokenTypeValueTextView.setVisibility(View.GONE);
                tokenValueTextView.setText(token.getToken());
            }

            if (token.getValidity() != Token.DEFAULT_VALIDITY) {
                expiresTextView.setVisibility(View.VISIBLE);
                tokenValidityTextView.setVisibility(View.VISIBLE);
                Date date = new Date(token.getValidity());
                tokenValidityTextView.setText(date.toString());
            } else {
                expiresTextView.setVisibility(View.GONE);
                tokenValidityTextView.setVisibility(View.GONE);
            }
        }
    }
}
