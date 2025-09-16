package org.zuma.examplePlugin.domain.dialog;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.dialog.constants.DialogPreset;
import org.zuma.examplePlugin.domain.heimscript.action.ScriptedAction;
import org.zuma.examplePlugin.domain.heimscript.condition.ScriptedCondition;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.quest.constants.QuestId;

import java.util.List;
import java.util.function.Supplier;

public class Dialogs {

    public static final Supplier<DialogBuilder> tutorial_survivalGuide = () -> DialogBuilder.deserialize("{\n" +
            "  \"id\": \"tutorial.survival-dialog\",\n" +
            "  \"steps\": [\n" +
            "    {\n" +
            "      \"uniqueIdentifier\": \"981acdf1-cbc3-481e-acae-444af668a0cc\",\n" +
            "      \"type\": \"ConditionStep\",\n" +
            "      \"conditions\": [\n" +
            "        \"quest_dialog_step_active_{stable_id_tutorial.survival-cook-fish}\"\n" +
            "      ],\n" +
            "      \"id\": \"tutorial.survival-dialog\",\n" +
            "      \"truthyFollowup\": {\n" +
            "        \"id\": \"tutorial.survival-dialog\",\n" +
            "        \"steps\": [\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"on.fish.cooked\",\n" +
            "            \"type\": \"ConditionStep\",\n" +
            "            \"conditions\": [\n" +
            "              \"inventory_contains_one_of_{item_id_1200}_{amount_1}\"\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\",\n" +
            "            \"falsyFollowup\": {\n" +
            "              \"id\": \"tutorial.survival-dialog\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"15df7413-2410-4111-be26-e9b94e7d6cca\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Cook and bring me §61 raw herring.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"You can either start a campfire, or use my stove.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"check.if.player.has.no.net\",\n" +
            "                  \"type\": \"ConditionStep\",\n" +
            "                  \"conditions\": [\n" +
            "                    \"inventory_contains_one_of_{item_id_1500}_{amount_1}\"\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"falsyFollowup\": {\n" +
            "                    \"id\": \"tutorial.survival-dialog\",\n" +
            "                    \"steps\": [\n" +
            "                      {\n" +
            "                        \"uniqueIdentifier\": \"check.if.player.has.no.net.or.raw.fish\",\n" +
            "                        \"type\": \"ConditionStep\",\n" +
            "                        \"conditions\": [\n" +
            "                          \"inventory_contains_one_of_{item_id_1300}_{amount_1}\"\n" +
            "                        ],\n" +
            "                        \"id\": \"tutorial.survival-dialog\",\n" +
            "                        \"falsyFollowup\": {\n" +
            "                          \"id\": \"tutorial.survival-dialog\",\n" +
            "                          \"steps\": [\n" +
            "                            {\n" +
            "                              \"uniqueIdentifier\": \"2af32d7d-ba39-42b7-b863-e6c27348ff9a\",\n" +
            "                              \"type\": \"PassiveDialogStep\",\n" +
            "                              \"content\": [\n" +
            "                                {\n" +
            "                                  \"line\": \"Oh, how did you manage to lose everything?\",\n" +
            "                                  \"typewriter\": true\n" +
            "                                }\n" +
            "                              ],\n" +
            "                              \"id\": \"tutorial.survival-dialog\",\n" +
            "                              \"actions\": [\n" +
            "                                \"give_item_{id_1500}_{amount_1}\"\n" +
            "                              ]\n" +
            "                            }\n" +
            "                          ]\n" +
            "                        },\n" +
            "                        \"content\": []\n" +
            "                      }\n" +
            "                    ]\n" +
            "                  },\n" +
            "                  \"content\": []\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            \"truthyFollowup\": {\n" +
            "              \"id\": \"tutorial.survival-dialog\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"361db2ae-b461-49db-b5b8-e2199f05c964\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Awesome! {PLAYER_NAME}\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"You are making progress!\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"db6053e9-2297-479d-a46d-766a79c9c529\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"This is just the small part of the possibilities.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"There are many skills and activities!\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"But you will have to figure those on your own.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"76362c2d-66aa-48e9-8f81-56e207e1faf5\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Now it’s time to say goodbye.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"It was pleasure meeting you, {PLAYER_NAME}!\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": [\n" +
            "                    \"dialog_quest_step_finish_{stable_id_tutorial.survival-cook-fish}\",\n" +
            "                    \"teleport_player_{world_world}_{x_192}_{y_73}_{z_-70}\",\n" +
            "                    \"clear_inventory\",\n" +
            "                    \"give_item_{id_700}_{amount_1}\",\n" +
            "                    \"give_item_{id_1100}_{amount_1}\",\n" +
            "                    \"give_item_{id_2400}_{amount_1}\",\n" +
            "                    \"give_item_{id_2500}_{amount_128}\",\n" +
            "                    \"give_item_{id_306}_{amount_1}\",\n" +
            "                    \"give_item_{id_1500}_{amount_1}\",\n" +
            "                    \"give_item_{id_1200}_{amount_20}\",\n" +
            "                    \"give_item_{id_3000}_{amount_2}\"\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            \"content\": []\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"content\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"uniqueIdentifier\": \"6bdd39c0-f7e1-4ce5-afd3-2eb7935423ac\",\n" +
            "      \"type\": \"ConditionStep\",\n" +
            "      \"conditions\": [\n" +
            "        \"quest_dialog_step_active_{stable_id_tutorial.survival-catch-fish}\"\n" +
            "      ],\n" +
            "      \"id\": \"tutorial.survival-dialog\",\n" +
            "      \"truthyFollowup\": {\n" +
            "        \"id\": \"tutorial.survival-dialog\",\n" +
            "        \"steps\": [\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"on.fish.catch\",\n" +
            "            \"type\": \"ConditionStep\",\n" +
            "            \"conditions\": [\n" +
            "              \"inventory_contains_one_of_{item_id_1300}_{amount_3}\"\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\",\n" +
            "            \"truthyFollowup\": {\n" +
            "              \"id\": \"tutorial.survival-dialog\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"48c3c07f-6608-46e5-8a29-62ff63f6c504\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"You’re raising my hopes. {PLAYER_NAME}\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"But theres not much you can do with a raw fish!\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"5a12b45f-ca02-4556-bcbb-f543552bcb7d\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"So now let's cook it!\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"In order to do that, you need a fire source.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"afdef1ed-f7d2-4303-8ed2-b00f324c334b\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Usually in the cities, you can simply use someones stove\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"But if you are in the wilderness, you will need to make a §6campfire\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"254bcd49-233d-4944-96ac-87f1c607c97c\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"To start a campfire, you need some §6logs§f & §6flint and steel.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"Then, you need to click a log with a flint in your inventory.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"Here, you can take mine. Or you can just use my stove.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"4476b0f4-9ed8-42d4-8221-847deb49d33a\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Then, cook and bring me §61 cooked herring.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": [\n" +
            "                    \"give_item_{id_1600}_{amount_3}\",\n" +
            "                    \"give_item_{id_2}_{amount_1}\",\n" +
            "                    \"dialog_quest_step_finish_{stable_id_tutorial.survival-catch-fish}\"\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            \"falsyFollowup\": {\n" +
            "              \"id\": \"tutorial.survival-dialog\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"cf1cc665-7063-4c84-bbda-b6fd9a67fd7a\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Bring me §63 raw herrings,§f {PLAYER_NAME}.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"Use the fishing spots behind this house.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": []\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            \"content\": []\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"fishing.net.check\",\n" +
            "            \"type\": \"ConditionStep\",\n" +
            "            \"conditions\": [\n" +
            "              \"inventory_contains_one_of_{item_id_1500}_{amount_1}\"\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\",\n" +
            "            \"falsyFollowup\": {\n" +
            "              \"id\": \"tutorial.survival-dialog\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"11ebca4c-5d2e-4f07-9b30-a65d5c7b2bd4\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Oh, how did you manage to lose the fishing net?\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": [\n" +
            "                    \"give_item_{id_1500}_{amount_1}\"\n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"b2d71e0e-5a50-4259-a393-e3fd39d833db\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Don't lose it this time!\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": []\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            \"content\": []\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"content\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"uniqueIdentifier\": \"93d0c859-2399-4b3f-8f16-11e4d73bb594\",\n" +
            "      \"type\": \"ConditionStep\",\n" +
            "      \"conditions\": [\n" +
            "        \"quest_dialog_step_active_{stable_id_tutorial.survival-kill-mobs}\"\n" +
            "      ],\n" +
            "      \"id\": \"tutorial.survival-dialog\",\n" +
            "      \"truthyFollowup\": {\n" +
            "        \"id\": \"tutorial.survival-dialog\",\n" +
            "        \"steps\": [\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"149e392c-f3ae-4a77-9b83-e08ee9b7443b\",\n" +
            "            \"type\": \"ConditionStep\",\n" +
            "            \"conditions\": [\n" +
            "              \"inventory_contains_one_of_{item_id_2600}_{amount_3}\"\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\",\n" +
            "            \"truthyFollowup\": {\n" +
            "              \"id\": \"tutorial.survival-dialog\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"70302339-ef97-4617-bf87-58107f73235a\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Oh lord, you are looking tired, haha\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"But good job!\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"9f4c3eca-8025-4fec-b9b6-0f600e20175f\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Okay, now let's teach you how to catch some fish\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"Some food will always be handy when you need\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"To restore some of your §chitspoints.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"580466bb-df8a-4dc7-a55a-e978ab6a1ed2\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Here, take this fishing net, and bring me §63 raw herrings.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"You can find the fishing spots behind my house.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": [\n" +
            "                    \"give_item_{id_1500}_{amount_1}\",\n" +
            "                    \"dialog_quest_step_finish_{stable_id_tutorial.survival-kill-mobs}\"\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            \"falsyFollowup\": {\n" +
            "              \"id\": \"tutorial.survival-dialog\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"454fdc4e-7807-4abc-981e-4f359e47c509\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Kill §63 goblins§f and bring me §63 bones.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"You can find goblins down the river.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"actions\": []\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            \"content\": []\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"content\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"uniqueIdentifier\": \"3c194caa-0a52-4998-9c85-69920db183b1\",\n" +
            "      \"type\": \"ConditionStep\",\n" +
            "      \"conditions\": [\n" +
            "        \"quest_dialog_step_active_{stable_id_tutorial.survival-talk-to}\"\n" +
            "      ],\n" +
            "      \"id\": \"tutorial.survival-dialog\",\n" +
            "      \"truthyFollowup\": {\n" +
            "        \"id\": \"tutorial.survival-dialog\",\n" +
            "        \"steps\": [\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"f01067d4-d21f-411c-9c37-4bb96f57147b\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"Hello there, stranger!\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\",\n" +
            "            \"actions\": []\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"fa45a399-33ae-48d4-97ee-f5f29f2ceb36\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"Hmm... Yeah\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\",\n" +
            "            \"actions\": []\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"47f0a999-67a0-4131-ba19-17dc3a3b5885\",\n" +
            "            \"type\": \"InteractiveDialogStep\",\n" +
            "            \"options\": [\n" +
            "              {\n" +
            "                \"label\": \"What?\",\n" +
            "                \"actions\": [],\n" +
            "                \"followup\": {\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"steps\": []\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Hm?\",\n" +
            "                \"actions\": [],\n" +
            "                \"followup\": {\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"steps\": []\n" +
            "                }\n" +
            "              }\n" +
            "            ],\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"*rolls his eyes*\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"a73a6f44-9625-4a95-91d0-96b3b5b56e63\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"Ahem, right, sorry\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"So, you are {PLAYER_NAME}?\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"Ok, there are some important things to go over.\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\",\n" +
            "            \"actions\": []\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"5b9d9ab7-51bc-42d1-95f3-a841b9f121e7\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"First of all, you must know how to protect yourself\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"There are many creatures out of there, some friendly\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"And others will take any chance to fight you\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\",\n" +
            "            \"actions\": []\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"9bbe478b-edea-4700-aee8-e1affc783698\",\n" +
            "            \"type\": \"InteractiveDialogStep\",\n" +
            "            \"options\": [\n" +
            "              {\n" +
            "                \"label\": \"A sword\",\n" +
            "                \"actions\": [\n" +
            "                  \"give_item_{id_700}_{amount_1}\"\n" +
            "                ],\n" +
            "                \"followup\": {\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"steps\": [\n" +
            "                    {\n" +
            "                      \"uniqueIdentifier\": \"cbe63edf-43a0-418c-846d-60aa4754bdcd\",\n" +
            "                      \"type\": \"PassiveDialogStep\",\n" +
            "                      \"content\": [\n" +
            "                        {\n" +
            "                          \"line\": \"Solid choice!\",\n" +
            "                          \"typewriter\": true\n" +
            "                        }\n" +
            "                      ],\n" +
            "                      \"id\": \"tutorial.survival-dialog\",\n" +
            "                      \"actions\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"uniqueIdentifier\": \"d1b745fa-36f6-4116-8279-8225c331534f\",\n" +
            "                      \"type\": \"TeleportBranchStep\",\n" +
            "                      \"content\": [],\n" +
            "                      \"teleportToUniqueIdentifier\": \"initial.countination\",\n" +
            "                      \"id\": \"tutorial.survival-dialog\",\n" +
            "                      \"actions\": []\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"A bow\",\n" +
            "                \"actions\": [\n" +
            "                  \"give_item_{id_2400}_{amount_1}\",\n" +
            "                  \"give_item_{id_2500}_{amount_64}\"\n" +
            "                ],\n" +
            "                \"followup\": {\n" +
            "                  \"id\": \"tutorial.survival-dialog\",\n" +
            "                  \"steps\": [\n" +
            "                    {\n" +
            "                      \"uniqueIdentifier\": \"c351b0eb-ee50-462a-86c4-2006dd1bdda5\",\n" +
            "                      \"type\": \"PassiveDialogStep\",\n" +
            "                      \"content\": [\n" +
            "                        {\n" +
            "                          \"line\": \"Perfect! Take this bow and arrows.\",\n" +
            "                          \"typewriter\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                          \"line\": \"Now open your inventory, and put arrows in the arrows slot.\",\n" +
            "                          \"typewriter\": true\n" +
            "                        }\n" +
            "                      ],\n" +
            "                      \"id\": \"tutorial.survival-dialog\",\n" +
            "                      \"actions\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"uniqueIdentifier\": \"2815dcf9-57ba-462b-9e7f-380c93840c5f\",\n" +
            "                      \"type\": \"TeleportBranchStep\",\n" +
            "                      \"content\": [],\n" +
            "                      \"teleportToUniqueIdentifier\": \"initial.countination\",\n" +
            "                      \"id\": \"tutorial.survival-dialog\",\n" +
            "                      \"actions\": []\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ],\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"What’s your favorite weapon?\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"initial.countination\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"Now, there are goblins down the river\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"They are driving me crazy\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\",\n" +
            "            \"actions\": []\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"3825f278-2f14-4fc2-81bf-c17fd519ecf2\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"I want you to kill §63 goblins§f and bring me §63 bones§f as proof.\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"Oh and yeah, you can also do special attacks!\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\",\n" +
            "            \"actions\": []\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"f2acca0d-490d-4e65-98bd-823a7a626a90\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"Hold §6[SNEAK]§f while attacking to do a special attack\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"Your special attack regenerates slowly during the fights.\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\",\n" +
            "            \"actions\": []\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"a4c42b01-604d-4b7e-83b6-509c0b55870e\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"Godspeed! Bring me §63 bones.\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"Just a tip: do not attack multiple goblins at same time!\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"Don't ask why, just trust the process.\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-dialog\",\n" +
            "            \"actions\": [\n" +
            "              \"dialog_quest_step_finish_{stable_id_tutorial.survival-talk-to}\"\n" +
            "            ]\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"content\": []\n" +
            "    }\n" +
            "  ]\n" +
            "}");

    public static final Supplier<DialogBuilder> tutorial_lumberjack = () -> DialogBuilder.deserialize("{\n" +
            "  \"id\": \"tutorial.survival-guide\",\n" +
            "  \"steps\": [\n" +
            "    {\n" +
            "      \"uniqueIdentifier\": \"73099b3f-38a0-4bae-9eed-6494661c0a18\",\n" +
            "      \"type\": \"ConditionStep\",\n" +
            "      \"conditions\": [\n" +
            "        \"quest_dialog_step_active_{stable_id_tutorial.lumberjack-cut-trees}\"\n" +
            "      ],\n" +
            "      \"id\": \"tutorial.survival-guide\",\n" +
            "      \"truthyFollowup\": {\n" +
            "        \"id\": \"tutorial.survival-guide\",\n" +
            "        \"steps\": [\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"check.for.logs\",\n" +
            "            \"type\": \"ConditionStep\",\n" +
            "            \"conditions\": [\n" +
            "              \"inventory_contains_one_of_{item_id_1600}_{amount_3}\"\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-guide\",\n" +
            "            \"truthyFollowup\": {\n" +
            "              \"id\": \"tutorial.survival-guide\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"chop.success\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Good job, {PLAYER_NAME}!\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"There are many kinds of trees in the wilderness\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"Some extremly rare, some not so much\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-guide\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"a5f75927-29ef-4571-a5e9-fcc898ac4f43\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Nevertheless, it's an essential material\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"For crafting bows, making campires and everything in between.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-guide\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"1e46b592-c072-4f49-9bc7-e65fac9f0ae3\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Now, go meet the §6survival guide.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"He will teach you how to not starve to death.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-guide\",\n" +
            "                  \"actions\": [\n" +
            "                    \"teleport_player_{world_world}_{x_2493}_{y_128}_{z_2411}\",\n" +
            "                    \"dialog_quest_step_finish_{stable_id_tutorial.lumberjack-cut-trees}\"\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            \"falsyFollowup\": {\n" +
            "              \"id\": \"tutorial.survival-guide\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"check.axe\",\n" +
            "                  \"type\": \"ConditionStep\",\n" +
            "                  \"conditions\": [\n" +
            "                    \"inventory_contains_one_of_{item_id_1000}_{amount_1}\"\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-guide\",\n" +
            "                  \"falsyFollowup\": {\n" +
            "                    \"id\": \"tutorial.survival-guide\",\n" +
            "                    \"steps\": [\n" +
            "                      {\n" +
            "                        \"uniqueIdentifier\": \"f5be871e-b243-479a-98ab-8d73af3d17ce\",\n" +
            "                        \"type\": \"PassiveDialogStep\",\n" +
            "                        \"content\": [\n" +
            "                          {\n" +
            "                            \"line\": \"What? Already lost the axe?\",\n" +
            "                            \"typewriter\": true\n" +
            "                          }\n" +
            "                        ],\n" +
            "                        \"id\": \"tutorial.survival-guide\",\n" +
            "                        \"actions\": [\n" +
            "                          \"give_item_{id_1000}_{amount_1}\"\n" +
            "                        ]\n" +
            "                      }\n" +
            "                    ]\n" +
            "                  }\n" +
            "                },\n" +
            "                {\n" +
            "                  \"uniqueIdentifier\": \"adc689cb-59a3-467b-9066-e3862f4767cc\",\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"§63 logs,§f cowboy. Aim for the weak spot!\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"id\": \"tutorial.survival-guide\"\n" +
            "                }\n" +
            "              ]\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"uniqueIdentifier\": \"10a33908-0289-4543-bb8f-3935e74c932e\",\n" +
            "      \"type\": \"ConditionStep\",\n" +
            "      \"conditions\": [\n" +
            "        \"quest_dialog_step_active_{stable_id_tutorial.lumberjack-talk-to}\"\n" +
            "      ],\n" +
            "      \"id\": \"tutorial.survival-guide\",\n" +
            "      \"truthyFollowup\": {\n" +
            "        \"id\": \"tutorial.survival-guide\",\n" +
            "        \"steps\": [\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"8a9f1eef-acca-47b8-b106-90308eacc502\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"Hey there!\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"See that tree over there?\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"That’s your first step to surviving out here.\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-guide\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"fdf0d9cb-3018-42ca-b766-2a94de6aa232\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"By themselves, logs won’t do you much good.\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"But you can use them as a material, or start a fire\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-guide\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"cab4125d-79b6-43e7-8586-9af8bd40177d\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"Ok, enough of talking.\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"My job is to teach you how to cut trees, that's it\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-guide\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"83ae92bb-945b-40b2-a6f2-77eb8f05800d\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"Here, take this axe, and bring me §63 logs.\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-guide\",\n" +
            "            \"actions\": [\n" +
            "              \"give_item_{id_1000}_{amount_1}\",\n" +
            "              \"dialog_quest_step_finish_{stable_id_tutorial.lumberjack-talk-to}\"\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"b0eb609f-81af-42c2-847d-c5f1bdafd715\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"Remember, you can’t just cut the tree anywhere.\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"Chop at the §6weak point.\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"The marker shows you where.\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial.survival-guide\"\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"falsyFollowup\": {\n" +
            "        \"id\": \"tutorial.survival-guide\",\n" +
            "        \"steps\": []\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}");


    public static final Supplier<DialogBuilder> tutorial_blacksmithDialog = () -> DialogBuilder.deserialize("{\n" +
            "  \"id\": \"tutorial-island.tutor-blacksmith\",\n" +
            "  \"steps\": [\n" +
            "    {\n" +
            "      \"type\": \"ConditionStep\",\n" +
            "      \"uniqueIdentifier\": \"9a7bf524-282b-4573-8a35-da46b8f80992\",\n" +
            "      \"content\": [],\n" +
            "      \"conditions\": [\n" +
            "        \"quest_dialog_step_active_{stable_id_tutorial.blacksmith-craft-items}\"\n" +
            "      ],\n" +
            "      \"truthyFollowup\": {\n" +
            "        \"id\": \"tutorial-island.tutor-blacksmith\",\n" +
            "        \"steps\": [\n" +
            "          {\n" +
            "            \"type\": \"ConditionStep\",\n" +
            "            \"uniqueIdentifier\": \"pickaxe.check\",\n" +
            "            \"content\": [],\n" +
            "            \"conditions\": [\n" +
            "              \"inventory_contains_one_of_{item_id_1100}_{amount_1}\"\n" +
            "            ],\n" +
            "            \"falsyFollowup\": {\n" +
            "              \"id\": \"tutorial-island.tutor-blacksmith\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"uniqueIdentifier\": \"b1f98f40-5cf3-422b-9a4c-718c73da7dd1\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"If you gonna lose your tools like this, we are getting nowhere!\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"actions\": [\n" +
            "                    \"give_item_{id_1100}_{amount_1}\"\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"ConditionStep\",\n" +
            "            \"uniqueIdentifier\": \"hammer.check\",\n" +
            "            \"content\": [],\n" +
            "            \"conditions\": [\n" +
            "              \"inventory_contains_one_of_{item_id_1}_{amount_1}\"\n" +
            "            ],\n" +
            "            \"falsyFollowup\": {\n" +
            "              \"id\": \"tutorial-island.tutor-blacksmith\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"uniqueIdentifier\": \"0f467344-1d65-41b2-bde1-7619fbab37f3\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Where is your hammer, rookie?\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"actions\": [\n" +
            "                    \"give_item_{id_1}_{amount_1}\"\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"ConditionStep\",\n" +
            "            \"uniqueIdentifier\": \"dagger.check\",\n" +
            "            \"content\": [],\n" +
            "            \"conditions\": [\n" +
            "              \"inventory_contains_one_of_{item_id_800}_{amount_1}\"\n" +
            "            ],\n" +
            "            \"truthyFollowup\": {\n" +
            "              \"id\": \"tutorial-island.tutor-blacksmith\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"uniqueIdentifier\": \"3a47a384-1561-41fc-9422-3e6204135f92\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Good job, {PLAYER_NAME}!\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"Remember, this is just the basics\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"uniqueIdentifier\": \"e2c09661-f0d8-4b5c-964f-fdb01ac692e0\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Keep mastering your skills\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"Explore the world to find new materials\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"And then §4Rune§eHeim §fwill finally get the hero it deserves\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"actions\": [\n" +
            "                    \"teleport_player_{world_world}_{x_2510}_{y_103}_{z_2410}\",\n" +
            "                    \"dialog_quest_step_finish_{stable_id_tutorial.blacksmith-craft-items}\"\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            \"falsyFollowup\": {\n" +
            "              \"id\": \"tutorial-island.tutor-blacksmith\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"uniqueIdentifier\": \"2ab8589b-a251-490f-a230-3bcb639dc530\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Huh, struggling already?\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"Remember, you need §e1 copper ore§f and §e1 tin ore\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"Then smelt it into a §ebronze bar\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"uniqueIdentifier\": \"8b8270e4-f8bd-47f2-83e9-ac60d94a0b28\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"After that, use your §ehammer§f on an §eanvil\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"And bring me §e1 Bronze Dagger\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"actions\": []\n" +
            "                }\n" +
            "              ]\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"ConditionStep\",\n" +
            "      \"uniqueIdentifier\": \"4d89e089-3275-4763-8d13-bb65c7901195\",\n" +
            "      \"content\": [],\n" +
            "      \"conditions\": [\n" +
            "        \"quest_dialog_step_active_{stable_id_tutorial.blacksmith-talk-to}\"\n" +
            "      ],\n" +
            "      \"truthyFollowup\": {\n" +
            "        \"id\": \"tutorial-island.tutor-blacksmith\",\n" +
            "        \"steps\": [\n" +
            "          {\n" +
            "            \"type\": \"ConditionStep\",\n" +
            "            \"uniqueIdentifier\": \"77c8b519-b651-4a91-89ff-9243d42fa108\",\n" +
            "            \"content\": [],\n" +
            "            \"conditions\": [\n" +
            "              \"inventory_contains_one_of_{item_id_1100}_{amount_1}\"\n" +
            "            ],\n" +
            "            \"falsyFollowup\": {\n" +
            "              \"id\": \"tutorial-island.tutor-blacksmith\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"uniqueIdentifier\": \"d9567dde-5bb3-42ee-b839-2b5284ab4696\",\n" +
            "                  \"content\": [],\n" +
            "                  \"actions\": [\n" +
            "                    \"give_item_{id_1100}_{amount_1}\"\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"InteractiveDialogStep\",\n" +
            "            \"uniqueIdentifier\": \"916d78e3-2d3d-407a-8224-81cc1cea83c3\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"Hello, {PLAYER_NAME}! You are new around here?\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"options\": [\n" +
            "              {\n" +
            "                \"label\": \"Yes!\",\n" +
            "                \"actions\": [],\n" +
            "                \"followup\": {\n" +
            "                  \"id\": \"tutorial-island.tutor-blacksmith\",\n" +
            "                  \"steps\": [\n" +
            "                    {\n" +
            "                      \"type\": \"PassiveDialogStep\",\n" +
            "                      \"uniqueIdentifier\": \"e788b6a4-3541-412f-bcef-9b51118566b4\",\n" +
            "                      \"content\": [\n" +
            "                        {\n" +
            "                          \"line\": \"Great! Welcome to the §4Rune§6Heim, §f{PLAYER_NAME}!\",\n" +
            "                          \"typewriter\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                          \"line\": \"Many great adventures await!\",\n" +
            "                          \"typewriter\": true\n" +
            "                        }\n" +
            "                      ],\n" +
            "                      \"actions\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"type\": \"PassiveDialogStep\",\n" +
            "                      \"uniqueIdentifier\": \"eac6ca56-46f1-43e5-92e3-103513bb7113\",\n" +
            "                      \"content\": [\n" +
            "                        {\n" +
            "                          \"line\": \"Unfortunately for you, you won't even get past the city gates!\",\n" +
            "                          \"typewriter\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                          \"line\": \"But you are lucky I am here, I'll help you out.\",\n" +
            "                          \"typewriter\": true\n" +
            "                        }\n" +
            "                      ],\n" +
            "                      \"actions\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"type\": \"PassiveDialogStep\",\n" +
            "                      \"uniqueIdentifier\": \"guide.start\",\n" +
            "                      \"content\": [\n" +
            "                        {\n" +
            "                          \"line\": \"The wilderness is full of different creatures\",\n" +
            "                          \"typewriter\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                          \"line\": \"And without any §6gear§f You’ll end up as a goblin’s dinner.\",\n" +
            "                          \"typewriter\": true\n" +
            "                        }\n" +
            "                      ],\n" +
            "                      \"actions\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"type\": \"PassiveDialogStep\",\n" +
            "                      \"uniqueIdentifier\": \"fa2bf496-b73b-46b9-a473-78a5f89ca907\",\n" +
            "                      \"content\": [\n" +
            "                        {\n" +
            "                          \"line\": \"First of all you need to mine exactly §e1 copper ore and §e1 tin ore.\",\n" +
            "                          \"typewriter\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                          \"line\": \"Then, smelt them into a §ebronze bar.\",\n" +
            "                          \"typewriter\": true\n" +
            "                        }\n" +
            "                      ],\n" +
            "                      \"actions\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"type\": \"PassiveDialogStep\",\n" +
            "                      \"uniqueIdentifier\": \"a9074ef0-00f9-4f7a-a0fd-88453cee1966\",\n" +
            "                      \"content\": [\n" +
            "                        {\n" +
            "                          \"line\": \"After that, use your hammer on an §eAnvil\",\n" +
            "                          \"typewriter\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                          \"line\": \"And select §eBronze Dagger\",\n" +
            "                          \"typewriter\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                          \"line\": \"Oh, I almost forgot! Take this hammer, too.\",\n" +
            "                          \"typewriter\": true\n" +
            "                        }\n" +
            "                      ],\n" +
            "                      \"actions\": [\n" +
            "                        \"give_item_{id_1}_{amount_1}\",\n" +
            "                        \"dialog_quest_step_finish_{stable_id_tutorial.blacksmith-talk-to}\"\n" +
            "                      ]\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Uh, no\",\n" +
            "                \"actions\": [],\n" +
            "                \"followup\": {\n" +
            "                  \"id\": \"tutorial-island.tutor-blacksmith\",\n" +
            "                  \"steps\": [\n" +
            "                    {\n" +
            "                      \"type\": \"PassiveDialogStep\",\n" +
            "                      \"uniqueIdentifier\": \"7e340949-3558-4bf4-b7cd-f00488caf3bc\",\n" +
            "                      \"content\": [\n" +
            "                        {\n" +
            "                          \"line\": \"Trying to be funny, eh?\",\n" +
            "                          \"typewriter\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                          \"line\": \"I remember everyone whom I taught how to survive.\",\n" +
            "                          \"typewriter\": true\n" +
            "                        }\n" +
            "                      ],\n" +
            "                      \"actions\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"type\": \"PassiveDialogStep\",\n" +
            "                      \"uniqueIdentifier\": \"95adfd2e-3a52-4a89-951c-afea8df217b5\",\n" +
            "                      \"content\": [\n" +
            "                        {\n" +
            "                          \"line\": \"You won't get even past the city gates!\",\n" +
            "                          \"typewriter\": true\n" +
            "                        }\n" +
            "                      ],\n" +
            "                      \"actions\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"type\": \"TeleportBranchStep\",\n" +
            "                      \"uniqueIdentifier\": \"be490770-c9e7-4ecd-ad34-bad6a93d63e7\",\n" +
            "                      \"teleportToUniqueIdentifier\": \"guide.start\",\n" +
            "                      \"content\": [],\n" +
            "                      \"actions\": []\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}");

    public static final Supplier<DialogBuilder> tutorial_peasant_minerDialog = () -> DialogBuilder.deserialize("{\n" +
            "  \"id\": \"tutorial-island.peasant-miner\",\n" +
            "  \"steps\": [\n" +
            "    {\n" +
            "      \"uniqueIdentifier\": \"eb2663b2-773c-4c63-b9dd-5a59a0ce499d\",\n" +
            "      \"type\": \"PassiveDialogStep\",\n" +
            "      \"content\": [\n" +
            "        {\n" +
            "          \"line\": \"W.. What?\",\n" +
            "          \"typewriter\": true\n" +
            "        },\n" +
            "        {\n" +
            "          \"line\": \"Who the hell are you?\",\n" +
            "          \"typewriter\": true\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": \"tutorial-island.peasant-miner\",\n" +
            "      \"actions\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"uniqueIdentifier\": \"7e22eef1-e04c-4a82-8eb5-889fe9d30885\",\n" +
            "      \"type\": \"PassiveDialogStep\",\n" +
            "      \"content\": [\n" +
            "        {\n" +
            "          \"line\": \"Oh, whatever. Go tell the §6tutor §fthat you're new around here.\",\n" +
            "          \"typewriter\": true\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": \"tutorial-island.peasant-miner\",\n" +
            "      \"actions\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"uniqueIdentifier\": \"77e2bac5-8ed5-40c3-b183-111472a7f9fa\",\n" +
            "      \"type\": \"ConditionStep\",\n" +
            "      \"conditions\": [\n" +
            "        \"inventory_contains_one_of_{item_id_1100}_{amount_1}\"\n" +
            "      ],\n" +
            "      \"id\": \"tutorial-island.peasant-miner\",\n" +
            "      \"falsyFollowup\": {\n" +
            "        \"id\": \"tutorial-island.peasant-miner\",\n" +
            "        \"steps\": [\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"934468c2-d529-4ee8-97e6-c32249909831\",\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"Wait\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"Here, this will be useful to you\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"id\": \"tutorial-island.peasant-miner\",\n" +
            "            \"actions\": [\n" +
            "              \"give_item_{id_1100}_{amount_1}\"\n" +
            "            ]\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"uniqueIdentifier\": \"ff5f0cf0-4d65-4d8c-82c0-7443cf9c5155\",\n" +
            "      \"type\": \"ConditionStep\",\n" +
            "      \"conditions\": [\n" +
            "        \"start_quest_if_requirements_met_{id_tutorial}\"\n" +
            "      ],\n" +
            "      \"id\": \"tutorial-island.peasant-miner\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"uniqueIdentifier\": \"b12e1c64-2d84-457a-a249-2e2fabcb9aab\",\n" +
            "      \"type\": \"PassiveDialogStep\",\n" +
            "      \"content\": [],\n" +
            "      \"id\": \"tutorial-island.peasant-miner\",\n" +
            "      \"actions\": [\n" +
            "        \"teleport_player_{world_world}_{x_2435}_{y_155}_{z_2485}\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}");

    public static final Supplier<DialogBuilder> sheepShearerDialog = () -> DialogBuilder.create("sheep-shearer-dialog")
            // Check if quest is finished
            .conditionStep(s -> s
                    .condition(ScriptedCondition.questFinished(QuestId.SHEEP_SHEARER))

                    // If quest finished
                    .truthyFollowUp(e -> e
                            .passiveStep(r -> r.text(List.of("It’s a peaceful day, isn’t it?", "Thanks to you, at least the flock isn’t plotting an escape.")))
                    )

                    // If quest NOT finished -> check if quest is started
                    .falsyFollowUp(r -> r
                            .conditionStep(q -> q
                                    .condition(ScriptedCondition.questStarted(QuestId.SHEEP_SHEARER))

                                    // If quest started
                                    .truthyFollowUp(w -> w
                                            .conditionStep(c -> c
                                                    .condition(ScriptedCondition.questDialogStepActive("sheep-shearer.wool-collected"))

                                                    // If we're at the "collected" dialog step -> check inventory removal
                                                    .truthyFollowUp(y -> y
                                                            .conditionStep(u -> u
                                                                    .condition(ScriptedCondition.inventoryContains(new ItemRecord(ItemId.BALL_OF_WOOL, 20)))

                                                                    // Inventory contains 20 balls of wool
                                                                    .truthyFollowUp(z -> z
                                                                            .passiveStep(l -> l
                                                                                    .text("Awesome, just what I needed!")
                                                                            ).conditionStep(ss -> ss.condition(ScriptedCondition.removeInventoryItemSuccessful(new ItemRecord(ItemId.BALL_OF_WOOL, 20)))
                                                                                    .truthyFollowUp(tt -> tt.passiveStep(ty -> ty.text("").action(ScriptedAction.dialogQuestStepFinish("sheep-shearer.finish")))
                                                                                            .passiveStep(yx -> yx.text(List.of("Thanks, {PLAYER_NAME}!", "You did a really great job!")))))

                                                                    )

                                                                    // Inventory doesn't contain 20 balls of wool
                                                                    .falsyFollowUp(z2 -> z2
                                                                            .passiveStep(v -> v.text(List.of("Good, you collected the wool", "Now bring it to me.")))
                                                                    )
                                                            )
                                                    )

                                                    // We're not at the "finish" dialog step
                                                    .falsyFollowUp(y2 -> y2
                                                            .passiveStep(i -> i.text(List.of("You gonna work, or just slacking around?", "I need 20 balls of wool.")))
                                                    )
                                            )
                                    )

                                    // If quest NOT started at all -> greeting + interactive options
                                    .falsyFollowUp(w2 -> w2
                                            .passiveStep(vv -> vv.text(List.of(
                                                    "Oi! What are you doing sneaking around my land?",
                                                    "You're not the one letting my sheep out?"
                                            )))
                                            .interactiveStep(i -> i.text("You looking for work or just snooping?")
                                                    .option("Maybe… what if I was?", o -> o.followupSteps(p -> p.passiveStep(n -> n.text("Don’t be clever with me."))))
                                                    .option("Sorry, won’t be long", o -> o.followupSteps(p -> p.passiveStep(n -> n.text("Fine - don’t let my sheep out!"))))
                                                    .option("I'm looking for work", o -> o.followupSteps(p -> p
                                                            // Quest start branch
                                                            .passiveStep(sp -> sp.text(List.of(
                                                                    "Good - I need twenty balls of wool from my sheep.",
                                                                    "Shear them, then spin the wool at the spinning wheel",
                                                                    "I'm pretty sure you can find spinning wheel at Drakkar."
                                                            )))
                                                            .conditionStep(ba -> ba
                                                                    .condition(ScriptedCondition.questStartSuccessful(QuestId.SHEEP_SHEARER))
                                                                    .truthyFollowUp(yy -> yy.passiveStep(ap -> ap.text("")
                                                                                    .action(ScriptedAction.giveItem(new ItemRecord(ItemId.SHEARS, 1))))
                                                                            .passiveStep(ys -> ys.text(List.of("Here, take these.", "And remember. 20 balls of wool!"))))
                                                            )
                                                    ))
                                            )
                                    )
                            )
                    )
            );


    public static final Supplier<DialogBuilder> bankerDialog = () -> DialogBuilder.create("banker-dialog")
            .passiveStep(p -> p.randomText(DialogPreset.WELCOME.getList()))
            .interactiveStep(c -> c.text("Open bank?")
                    .option("§aYes" + " §f" + Icon.MONEY_BAG.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNo", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));

    public static final Supplier<DialogBuilder> investmentBankerDialog = () -> DialogBuilder.create("investment-banker-dialog")
            .passiveStep(p -> p.randomText(List.of("Welcome, {PLAYER_NAME}.",
                    "Good to see you, {PLAYER_NAME}.",
                    "Ready to invest?",
                    "Greetings, {PLAYER_NAME}.",
                    "Let’s talk business.")))
            .interactiveStep(c -> c.randomText(DialogPreset.WANT_TO_SEE_STOCK.getList())
                    .option("§aSure" + " §f" + Icon.COIN_HANDFUL.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNot now", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));


    public static final Supplier<DialogBuilder> auctioneerDialog = () -> DialogBuilder.create("auctioneer-dialog")
            .passiveStep(p -> p.randomText(DialogPreset.WELCOME.getList()))
            .interactiveStep(c -> c.text("Open auction?")
                    .option("§aYes" + " §f" + Icon.TRADE.get(), b -> b.action("dialog_holder_forward_interaction"))
                    .option("§cNo", n -> n.followupSteps(d -> d.passiveStep(p -> p.randomText(DialogPreset.FAREWELL.getList())))));


    public static final Supplier<DialogBuilder> farmHorseTrader = () -> DialogBuilder.create("farm-horse-trader-dialog")
            .passiveStep(p -> p.randomText(DialogPreset.WELCOME.getList()))
            .passiveStep(b -> b.text(List.of(
                    "Looking for a trusty steed?",
                    "This brown farm horse is strong and steady."
            )))
            .passiveStep(b -> b.text(List.of(
                    "§6500 gold coins§f, fair price.",
                    "Interested?"
            )))
            .interactiveStep(c -> c.text("Buy Brown Farm Horse for §6500gp?")
                    .option("§aYes" + " §f" + Icon.MONEY_BAG.get(), q -> q.followupSteps(w ->
                            w.conditionStep(e -> e.condition(ScriptedCondition.removePouchCoinsSuccessful(500))
                                    .truthyFollowUp(g -> g.passiveStep(t ->
                                                    t.action(ScriptedAction.giveItem(new ItemRecord(ItemId.BROWN_FARM_HORSE_MOUNT, 1))))
                                            .passiveStep(o -> o.text(List.of(
                                                    "Pleasure doing business!",
                                                    "Take good care of your new mount."
                                            )))
                                            .passiveStep(i -> i.text(List.of(
                                                    "Ride far and fast,",
                                                    "and she’ll grow strong with you."
                                            )))
                                    )
                                    .falsyFollowUp(y -> y.passiveStep(u -> u.text(
                                            List.of("Looks like business isn’t your strong suit!", "Come back when you have at least §6500gp.")
                                    )))
                            )))
                    .option("§cNo", n -> n.followupSteps(d -> d.passiveStep(p -> p.text(
                            "Suit yourself. Safe travels!"
                    )))));

    public static final Supplier<DialogBuilder> fairyTeacher = () -> DialogBuilder.create("fairy-teacher-dialog")
            .passiveStep(p -> p.text(java.util.List.of(
                    "Psst… hear that chime?",
                    "Not wind. Fairies.",
                    "Small. Shy. Real.",
                    "I’ve chased them all my life."
            )))
            .conditionStep(c -> c.condition(ScriptedCondition.inventoryContainsAnyOf(
                                    java.util.List.of(
                                            new ItemRecord(ItemId.FAIRY_OF_DARKNESS, 1),
                                            new ItemRecord(ItemId.FAIRY_OF_FROST, 1),
                                            new ItemRecord(ItemId.FAIRY_OF_HOPE, 1),
                                            new ItemRecord(ItemId.FAIRY_OF_LIGHT, 1),
                                            new ItemRecord(ItemId.FAIRY_OF_SILENCE, 1)
                                    )
                            ))
                            .truthyFollowUp(g -> g
                                    .passiveStep(t -> t.text(java.util.List.of(
                                            "By the stars… you’ve got one!",
                                            "A true fairy. Rare and fragile.",
                                            "I can hardly believe my eyes."
                                    )))
                                    .passiveStep(a -> a.text(java.util.List.of(
                                            "C-can I touch it?"
                                    )))
                                    .passiveStep(a -> a.text(java.util.List.of(
                                            "No, Abraham! That’s theirs!",
                                            "Fine, fine… then let me teach you."
                                    )))
                                    // --- tutorial part unchanged ---
                                    .interactiveStep(a -> a.text("What would you like to know?").loop()
                                            .option(Icon.SWORD.get() + " Training", b -> b.followupSteps(q -> q
                                                    .passiveStep(d -> d.text(java.util.List.of(
                                                            "Fairies gain XP slowly",
                                                            "When their XP bar fills, they can eat a fruit.",
                                                            "Each fruit raises their level — but only if ready."
                                                    )))
                                                    .passiveStep(d -> d.text(java.util.List.of(
                                                            "Don’t rush it. If they’re not ready, fruit won’t work.",
                                                            "Patience makes them stronger."
                                                    )))
                                            ))
                                            .option(Icon.FAIRY.get() + " Feeding", b -> b.followupSteps(q -> q
                                                    .passiveStep(d -> d.text(java.util.List.of(
                                                            "Fairies lose stamina when active.",
                                                            "If it hits zero, they stop gaining XP or giving bonuses.",
                                                            "Feed them to restore stamina up to 100."
                                                    )))
                                                    .passiveStep(d -> d.text(java.util.List.of(
                                                            "Keep them fed, and they’ll keep learning.",
                                                            "Too hungry = no progress."
                                                    )))
                                            ))
                                            .option(Icon.BLUE_GEM.get() + " Fruits", b -> b.followupSteps(q -> q
                                                    .passiveStep(d -> d.text(java.util.List.of(
                                                            "Each fairy fruit is unique.",
                                                            "When consumed by fairy, it adds a special upgrades.",
                                                            "That’s how fairies gain new powers."
                                                    )))
                                                    .passiveStep(d -> d.text(java.util.List.of(
                                                            "A maxed fairy has all its upgrades.",
                                                            "Generations raise the ceiling even higher."
                                                    )))
                                            ))
                                            .option(Icon.ERROR.get() + " Never mind")
                                    )
                            )
                            .falsyFollowUp(y -> y
                                    .passiveStep(u -> u.text(java.util.List.of(
                                            "They give real power.",
                                            "But no one believes me.",
                                            "No one else sees them."
                                    )))
                                    .passiveStep(v -> v.text(java.util.List.of(
                                            "Still… one might appear to you.",
                                            "Look at twilight. Listen for chimes.",
                                            "Belief lights the path."
                                    )))
                            )
            )

            // Parting lines
            .passiveStep(end -> end.text(java.util.List.of(
                    "Farewell, wanderer.",
                    "Eyes open at dusk."
            )));
}
