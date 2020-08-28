package de.telekom.scstoragedemo;

import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;

/**
 * Created by Alex.Graur@endava.com at 8/26/2020
 */
public interface OnItemInteractionListener {

    void onItemClicked(ItemEnvelope item);

    void onDeleteClicked(ItemEnvelope item);
}
