package org.zuma.examplePlugin.domain.gui.impl.individual.storage;

import org.zuma.examplePlugin.domain.gui.base.LimitedByTypeStorageInventory;
import org.zuma.examplePlugin.domain.storage.RepositoryQueryLockController;
import org.zuma.examplePlugin.domain.storage.constants.StorageCategory;

import java.util.Set;

public class LargeBagInventory extends LimitedByTypeStorageInventory {
    public LargeBagInventory(String title, int id, RepositoryQueryLockController repositoryQueryLockController) {
        super(title, id, StorageCategory.ANY, repositoryQueryLockController, Set.of(
                0, 1, 2, 3, 4, 5, 6, 7, 8,
                9, 10, 11, 12, 13, 14, 15, 16,
                17, 18, 19, 24,
                25, 26, 27, 28,
                33, 34, 35, 36, 37, 42, 43, 44, 45, 46, 47, 48,
                49, 50, 51, 52, 53));
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }
}
