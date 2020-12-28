package com.jonahsebright.billingtest.util;

import java.util.ArrayList;

public interface IdContainer {
    static int findPositionOf(String searchId, ArrayList<IdContainer> idContainers) {
        for (int i = 0; i < idContainers.size(); i++) {
            if (idContainers.get(i).getProductId().equals(searchId)) return i;
        }
        return -1;
    }

    static <T extends IdContainer> T findItem(String searchId, ArrayList<T> idContainers) {
        for (T idContainer : idContainers) {
            String id = idContainer.getProductId();
            if (id != null) {
                if (searchId.equals(id)) {
                    return idContainer;
                }
            }
        }
        return null;
    }

    String getProductId();
}
