package org.zuma.examplePlugin.domain.dialog;

import org.zuma.examplePlugin.domain.dialog.base.DialogStep;

import javax.annotation.Nullable;
import java.util.List;

public record InteractiveDialogOption(String content, @Nullable List<DialogStep> subContent,
                                      List<String> actions, @Nullable Runnable runnable) {


}
