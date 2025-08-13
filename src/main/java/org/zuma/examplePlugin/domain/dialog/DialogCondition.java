package org.zuma.examplePlugin.domain.dialog;

import org.zuma.examplePlugin.domain.dialog.base.DialogStep;

import javax.annotation.Nullable;
import java.util.List;

public record DialogCondition(String condition, @Nullable List<DialogStep> truthyFollowUp,
                              @Nullable List<DialogStep> falsyFollowUp) {


}
