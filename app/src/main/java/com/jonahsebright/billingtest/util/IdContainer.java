package com.jonahsebright.billingtest.util;

import java.util.ArrayList;

public interface IdContainer {
    static <T extends IdContainer> int findPositionOf(String searchId, ArrayList<T> idContainers) {
        for (int i = 0; i < idContainers.size(); i++) {
            if (idContainers.get(i).getId().equals(searchId)) return i;
        }
        return -1;
    }

    static <T extends IdContainer> T findItem(String searchId, ArrayList<T> idContainers) {
        for (T idContainer : idContainers) {
            String id = idContainer.getId();
            if (id != null) {
                if (searchId.equals(id)) {
                    return idContainer;
                }
            }
        }
        return null;
    }

    String getId();
}
