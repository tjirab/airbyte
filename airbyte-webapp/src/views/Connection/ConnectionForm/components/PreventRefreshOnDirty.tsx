import { useCallback } from "react";
import { useUnmount } from "react-use";

import { useConfirmationModalService } from "hooks/services/ConfirmationModal";

export const usePreventRefreshOnDirty = (dirty: boolean, refreshSourceSchema: () => Promise<void>) => {
  const { openConfirmationModal, closeConfirmationModal } = useConfirmationModalService();

  useUnmount(() => {
    closeConfirmationModal();
  });

  return useCallback(() => {
    if (dirty) {
      openConfirmationModal({
        title: "connection.updateSchema.formChanged.title",
        text: "connection.updateSchema.formChanged.text",
        submitButtonText: "connection.updateSchema.formChanged.confirm",
        onSubmit: () => {
          closeConfirmationModal();
          refreshSourceSchema();
        },
      });
    } else {
      refreshSourceSchema();
    }
  }, [closeConfirmationModal, dirty, openConfirmationModal, refreshSourceSchema]);
};
