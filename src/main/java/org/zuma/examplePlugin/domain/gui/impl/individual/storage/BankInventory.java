package org.zuma.examplePlugin.domain.gui.impl.individual.storage;

import org.zuma.examplePlugin.domain.gui.base.StorageInventory;
import org.zuma.examplePlugin.domain.storage.RepositoryQueryLockController;
import org.zuma.examplePlugin.domain.storage.constants.StorageType;

import java.util.Arrays;
import java.util.HashSet;

public class BankInventory extends StorageInventory {
    public BankInventory(String title, int id, RepositoryQueryLockController repositoryQueryLockController) {
        super(title, id, 6, 3, StorageType.BANK, repositoryQueryLockController, new HashSet<>(Arrays.asList(0, 1, 9, 10)));
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }
}
