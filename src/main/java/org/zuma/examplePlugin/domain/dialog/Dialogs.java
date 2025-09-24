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

    public static final Supplier<DialogBuilder> tutorial_survivalGuide = () -> DialogBuilder.deserialize("{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"cb700178-f54d-4a1b-89c6-d149d75e5f5f\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.survival-collect-bones}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"TeleportBranchStep\",\"uniqueIdentifier\":\"48e8e0a8-854e-4dbd-9a66-90819667a866\",\"teleportToUniqueIdentifier\":\"struggling.killing\",\"content\":[],\"actions\":[],\"uuid\":\"8719a8d2-9a4c-4c03-bf99-14a3045e23e1\"}],\"uuid\":\"a8b38ec8-6bcc-4cf3-98c9-2990c4e7547b\"},\"uuid\":\"9aa725b3-be99-4da5-8b76-a5e3939384a4\"},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"981acdf1-cbc3-481e-acae-444af668a0cc\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.survival-cook-fish}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"on.fish.cooked\",\"content\":[],\"conditions\":[\"inventory_contains_one_of_{item_id_1200}_{amount_1}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"361db2ae-b461-49db-b5b8-e2199f05c964\",\"content\":[{\"line\":\"Awesome! {PLAYER_NAME}\",\"typewriter\":true},{\"line\":\"You are making progress!\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"11bd419e-a634-4d5d-b668-1a9b170ce16a\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"db6053e9-2297-479d-a46d-766a79c9c529\",\"content\":[{\"line\":\"This is just the small part of the possibilities.\",\"typewriter\":true},{\"line\":\"There are many skills and activities!\",\"typewriter\":true},{\"line\":\"But you will have to figure those on your own.\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"ad4eb4f9-6372-4ebd-8460-39b2cce0fa18\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"76362c2d-66aa-48e9-8f81-56e207e1faf5\",\"content\":[{\"line\":\"Now it’s time to say goodbye.\",\"typewriter\":true},{\"line\":\"It was pleasure meeting you, {PLAYER_NAME}!\",\"typewriter\":true}],\"actions\":[\"quest_step_finish_{stable_id_tutorial.survival-cook-fish}\",\"teleport_player_{world_world}_{x_192}_{y_73}_{z_-70}\",\"clear_inventory\"],\"uuid\":\"3f2c8e0c-916d-4e09-a4e6-c5aba56dd4ec\"}],\"uuid\":\"6c59187b-3b3e-4b71-8c46-72a0eb080703\"},\"falsyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"struggling.cooking\",\"content\":[{\"line\":\"Cook and bring me §61 raw herring.\",\"typewriter\":true},{\"line\":\"You can either start a campfire, or use my stove.\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"cc7ddb7a-6b41-47f7-bbe5-880fe7646e89\"},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"check.if.player.has.no.net\",\"content\":[],\"conditions\":[\"inventory_contains_one_of_{item_id_1500}_{amount_1}\"],\"logicalOperator\":\"AND\",\"falsyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"check.if.player.has.no.net.or.raw.fish\",\"content\":[],\"conditions\":[\"inventory_contains_one_of_{item_id_1300}_{amount_1}\"],\"logicalOperator\":\"AND\",\"falsyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"2af32d7d-ba39-42b7-b863-e6c27348ff9a\",\"content\":[{\"line\":\"Oh, how did you manage to lose everything?\",\"typewriter\":true}],\"actions\":[\"give_item_{id_1500}_{amount_1}\"],\"uuid\":\"2a8f4628-421d-45ea-9d67-164d0f8f07e3\"}],\"uuid\":\"d149eea9-e6b4-4637-bc2a-6183f82ae65d\"},\"uuid\":\"cd92cd3f-d5ca-4b12-90e8-bf513ffd38c3\"}],\"uuid\":\"f720609a-6a41-46a2-aac5-b393a1cf599d\"},\"uuid\":\"0cf1ad21-ef3a-49c5-aecc-b9cfc5722da0\"}],\"uuid\":\"80640365-9481-4fdb-a367-ea9a0d2cc0c1\"},\"uuid\":\"c3476824-abcd-429e-a3f7-b45cd650b09a\"}],\"uuid\":\"b66c5ade-fd3d-4b09-848c-637db97754c3\"},\"uuid\":\"c677cc44-f61c-438b-af1f-aa49a816947a\"},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"797c964e-683e-4602-9597-d008de454161\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.survival-collect-herrings}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"TeleportBranchStep\",\"uniqueIdentifier\":\"6fded001-7a8c-419f-96b6-74e5c9c71cad\",\"teleportToUniqueIdentifier\":\"struggling.fishing\",\"content\":[],\"actions\":[],\"uuid\":\"9d110a06-be17-40db-b846-b95d1b0f4ef2\"}],\"uuid\":\"cb78289a-97a5-4603-abad-894d0b805d8f\"},\"uuid\":\"bd64c536-4962-47c9-90ef-177d7b8e7f40\"},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"77e2039a-67a6-4713-906e-b7069af83669\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.survival-cook-herring}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"TeleportBranchStep\",\"uniqueIdentifier\":\"100b594b-7633-439d-a887-7a118242e194\",\"teleportToUniqueIdentifier\":\"struggling.cooking\",\"content\":[],\"actions\":[],\"uuid\":\"cbe7971d-3ab3-4ee9-9599-520a91ef0cd7\"}],\"uuid\":\"8c90bf2d-83a3-4b61-a757-c636c444e165\"},\"uuid\":\"397b9b13-f2e0-4ac2-a38a-8180a5f2ceec\"},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"6bdd39c0-f7e1-4ce5-afd3-2eb7935423ac\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.survival-bring-herrings}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"on.fish.catch\",\"content\":[],\"conditions\":[\"inventory_contains_one_of_{item_id_1300}_{amount_3}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"48c3c07f-6608-46e5-8a29-62ff63f6c504\",\"content\":[{\"line\":\"You’re raising my hopes. {PLAYER_NAME}\",\"typewriter\":true},{\"line\":\"But theres not much you can do with a raw fish!\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"bcfa65b3-c4bc-44de-b9fa-3e9aa5ed5dff\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"5a12b45f-ca02-4556-bcbb-f543552bcb7d\",\"content\":[{\"line\":\"So now let's cook it!\",\"typewriter\":true},{\"line\":\"In order to do that, you need a fire source.\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"c6933b56-3c80-467a-aeeb-b0d255ca8b58\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"afdef1ed-f7d2-4303-8ed2-b00f324c334b\",\"content\":[{\"line\":\"Usually in the cities, you can simply use someones stove\",\"typewriter\":true},{\"line\":\"But if you are in the wilderness, you will need to make a §6campfire\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"a91c2ea5-6807-4ecf-9f8f-9d91f6004028\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"254bcd49-233d-4944-96ac-87f1c607c97c\",\"content\":[{\"line\":\"To start a campfire, you need some §6logs§f & §6flint and steel.\",\"typewriter\":true},{\"line\":\"Then, you need to click a log with a flint in your inventory.\",\"typewriter\":true},{\"line\":\"Here, you can take mine. Or you can just use my stove.\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"dcfe0153-b414-443e-9cd2-300149d75620\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"4476b0f4-9ed8-42d4-8221-847deb49d33a\",\"content\":[{\"line\":\"Then, cook and bring me §61 cooked herring.\",\"typewriter\":true}],\"actions\":[\"give_item_{id_1600}_{amount_3}\",\"give_item_{id_2}_{amount_1}\",\"quest_step_finish_{stable_id_tutorial.survival-bring-herrings}\"],\"uuid\":\"1e34f393-dbad-4842-b898-8736dbec4502\"}],\"uuid\":\"2c6ab6fd-8606-4645-b642-e03644caca45\"},\"falsyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"struggling.fishing\",\"content\":[{\"line\":\"Bring me §63 raw herrings,§f {PLAYER_NAME}.\",\"typewriter\":true},{\"line\":\"Use the fishing spots behind this house.\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"c72b37f5-5de4-4dc7-a0dc-14f753623f0f\"},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"fishing.net.check\",\"content\":[],\"conditions\":[\"inventory_contains_one_of_{item_id_1500}_{amount_1}\"],\"logicalOperator\":\"AND\",\"falsyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"11ebca4c-5d2e-4f07-9b30-a65d5c7b2bd4\",\"content\":[{\"line\":\"Oh, how did you manage to lose the fishing net?\",\"typewriter\":true}],\"actions\":[\"give_item_{id_1500}_{amount_1}\"],\"uuid\":\"6a606944-ff45-42c9-9838-ac1cb452581f\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"b2d71e0e-5a50-4259-a393-e3fd39d833db\",\"content\":[{\"line\":\"Don't lose it this time!\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"e4f555d5-8b89-417b-b401-aeb55714effd\"}],\"uuid\":\"42954db9-ed35-430b-8b07-8e84f14ae38f\"},\"uuid\":\"ffd078d2-3ed8-44e3-9339-eb83cc7c1420\"}],\"uuid\":\"ffb24498-d953-478f-9f77-e1b90602c07b\"},\"uuid\":\"6a829c9d-8d93-46d5-ab2f-8c24ec1b304b\"}],\"uuid\":\"0912f9ce-fdfd-4c92-9ec8-bad69b25649f\"},\"uuid\":\"76092be4-646f-4c33-82db-ec3f83776e94\"},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"93d0c859-2399-4b3f-8f16-11e4d73bb594\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.survival-bring-bones}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"149e392c-f3ae-4a77-9b83-e08ee9b7443b\",\"content\":[],\"conditions\":[\"inventory_contains_one_of_{item_id_2600}_{amount_3}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"70302339-ef97-4617-bf87-58107f73235a\",\"content\":[{\"line\":\"Oh lord, you are looking tired, haha\",\"typewriter\":true},{\"line\":\"But good job!\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"e03f5101-b354-4b51-acc9-449a46778f04\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"9f4c3eca-8025-4fec-b9b6-0f600e20175f\",\"content\":[{\"line\":\"Okay, now let's teach you how to catch some fish\",\"typewriter\":true},{\"line\":\"Some food will always be handy when you need\",\"typewriter\":true},{\"line\":\"To restore some of your §chitspoints.\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"0c08418d-e021-4880-b86c-1a68ad6c3196\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"580466bb-df8a-4dc7-a55a-e978ab6a1ed2\",\"content\":[{\"line\":\"Here, take this fishing net, and bring me §63 raw herrings.\",\"typewriter\":true},{\"line\":\"You can find the fishing spots behind my house.\",\"typewriter\":true}],\"actions\":[\"give_item_{id_1500}_{amount_1}\",\"quest_step_finish_{stable_id_tutorial.survival-bring-bones}\"],\"uuid\":\"dac1715b-b0ad-482d-92d3-786a8329e907\"}],\"uuid\":\"735e985c-4d21-45d3-848e-1b25dae2ec5b\"},\"falsyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"struggling.killing\",\"content\":[{\"line\":\"Kill §63 goblins§f and bring me §63 bones.\",\"typewriter\":true},{\"line\":\"You can find goblins down the river.\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"ebef654f-589a-4619-b4eb-af43c7812ee1\"}],\"uuid\":\"12d4940e-36a8-4c68-91db-f2ed4c88863d\"},\"uuid\":\"6923f5ea-2338-44aa-8d57-014cf7fb457f\"}],\"uuid\":\"eb479e81-1fef-4e36-971b-8b26010869d1\"},\"uuid\":\"53be8609-25ee-414c-9911-196e543c7778\"},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"3c194caa-0a52-4998-9c85-69920db183b1\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.survival-talk-to}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"f01067d4-d21f-411c-9c37-4bb96f57147b\",\"content\":[{\"line\":\"Hello there, stranger!\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"87b4d2b8-c47f-4def-a452-d313324e2cb9\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"fa45a399-33ae-48d4-97ee-f5f29f2ceb36\",\"content\":[{\"line\":\"Hmm... Yeah\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"57160e22-12f9-4e94-a7c1-e0c82e0671e1\"},{\"type\":\"InteractiveDialogStep\",\"uniqueIdentifier\":\"47f0a999-67a0-4131-ba19-17dc3a3b5885\",\"content\":[{\"line\":\"*rolls his eyes*\",\"typewriter\":true}],\"options\":[{\"label\":\"What?\",\"actions\":[],\"followup\":{\"id\":\"followup-empty\",\"steps\":[],\"uuid\":\"33357299-eb35-4464-b6b2-4a81bafcdcbc\"}},{\"label\":\"Hm?\",\"actions\":[],\"followup\":{\"id\":\"followup-empty\",\"steps\":[],\"uuid\":\"943a1bba-8111-4278-9aec-8ea885abca43\"}}],\"uuid\":\"56b9a823-7d80-4927-9fa4-e45217cbd9f1\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"a73a6f44-9625-4a95-91d0-96b3b5b56e63\",\"content\":[{\"line\":\"Ahem, right, sorry\",\"typewriter\":true},{\"line\":\"So, you are {PLAYER_NAME}?\",\"typewriter\":true},{\"line\":\"Ok, there are some important things to go over.\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"71ff78d7-c06d-4eab-943d-f9a4e719b154\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"5b9d9ab7-51bc-42d1-95f3-a841b9f121e7\",\"content\":[{\"line\":\"First of all, you must know how to protect yourself\",\"typewriter\":true},{\"line\":\"There are many creatures out of there, some friendly\",\"typewriter\":true},{\"line\":\"And others will take any chance to fight you\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"fe35b989-c94d-44ad-bdfa-e2fd57f86df8\"},{\"type\":\"InteractiveDialogStep\",\"uniqueIdentifier\":\"9bbe478b-edea-4700-aee8-e1affc783698\",\"content\":[{\"line\":\"What’s your favorite weapon?\",\"typewriter\":true}],\"options\":[{\"label\":\"A sword\",\"actions\":[\"give_item_{id_700}_{amount_1}\"],\"followup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"cbe63edf-43a0-418c-846d-60aa4754bdcd\",\"content\":[{\"line\":\"Solid choice!\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"3ed897b9-fb5e-48e6-87b4-21d7cdf35089\"},{\"type\":\"TeleportBranchStep\",\"uniqueIdentifier\":\"d1b745fa-36f6-4116-8279-8225c331534f\",\"teleportToUniqueIdentifier\":\"initial.countination\",\"content\":[],\"actions\":[],\"uuid\":\"4eb13b5c-dac7-4663-b79c-210f980546a8\"}],\"uuid\":\"9a68e400-0e86-4830-ac1d-f614415115ae\"}},{\"label\":\"A bow\",\"actions\":[\"give_item_{id_2400}_{amount_1}\",\"give_item_{id_2500}_{amount_64}\"],\"followup\":{\"id\":\"tutorial.survival-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"c351b0eb-ee50-462a-86c4-2006dd1bdda5\",\"content\":[{\"line\":\"Perfect! Take this bow and arrows.\",\"typewriter\":true},{\"line\":\"Now open your inventory, and put arrows in the arrows slot.\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"1429c16b-f059-4dcf-a5c7-5056da7561eb\"},{\"type\":\"TeleportBranchStep\",\"uniqueIdentifier\":\"2815dcf9-57ba-462b-9e7f-380c93840c5f\",\"teleportToUniqueIdentifier\":\"initial.countination\",\"content\":[],\"actions\":[],\"uuid\":\"5e012616-51c3-4ebb-81f2-11359cbb5271\"}],\"uuid\":\"be016919-eadd-4eac-ab8a-6cfb87c344cf\"}}],\"uuid\":\"3b5de258-83e6-45f7-94cd-d1eaf41f72fb\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"initial.countination\",\"content\":[{\"line\":\"Now, there are goblins down the river\",\"typewriter\":true},{\"line\":\"They are driving me crazy\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"8caeed0e-a1d2-49fd-bc7b-63e2f97f05ee\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"3825f278-2f14-4fc2-81bf-c17fd519ecf2\",\"content\":[{\"line\":\"I want you to kill §63 goblins§f and bring me §63 bones§f as proof.\",\"typewriter\":true},{\"line\":\"Oh and yeah, you can also do special attacks!\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"53669abc-3b5f-479c-890a-ddb7b22ab1eb\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"f2acca0d-490d-4e65-98bd-823a7a626a90\",\"content\":[{\"line\":\"Hold §6[SNEAK]§f while attacking to do a special attack\",\"typewriter\":true},{\"line\":\"Your special attack regenerates slowly during the fights.\",\"typewriter\":true}],\"actions\":[],\"uuid\":\"18817f1d-eacc-42c1-948d-73c63d790c5b\"},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"a4c42b01-604d-4b7e-83b6-509c0b55870e\",\"content\":[{\"line\":\"Godspeed! Bring me §63 bones.\",\"typewriter\":true},{\"line\":\"Just a tip: do not attack multiple goblins at same time!\",\"typewriter\":true},{\"line\":\"Don't ask why, just trust the process.\",\"typewriter\":true}],\"actions\":[\"quest_step_finish_{stable_id_tutorial.survival-talk-to}\"],\"uuid\":\"0bb58923-1589-4faf-ab5d-112ccefa33f1\"}],\"uuid\":\"72f7fbf5-4667-44c4-aa7a-21a32a1247d2\"},\"uuid\":\"41fc799a-eb8c-4421-a827-36466b261e67\"}],\"uuid\":\"5a288702-d5a4-4ecd-bd0e-6dc4c913cf9f\"}");
    public static final Supplier<DialogBuilder> tutorial_lumberjack = () -> DialogBuilder.deserialize("{\"id\":\"tutorial.survival-guide\",\"steps\":[{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"be20eee3-fe8d-445e-9fd9-cf8b93157f6d\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.lumberjack-collect-logs}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-guide\",\"steps\":[{\"type\":\"TeleportBranchStep\",\"uniqueIdentifier\":\"a351365d-b87f-4c9b-aea2-c7d7ec2ff09c\",\"teleportToUniqueIdentifier\":\"struggling.block\",\"content\":[]}]}},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"73099b3f-38a0-4bae-9eed-6494661c0a18\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.lumberjack-bring-logs}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-guide\",\"steps\":[{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"check.for.logs\",\"content\":[],\"conditions\":[\"inventory_contains_one_of_{item_id_1600}_{amount_3}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-guide\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"chop.success\",\"content\":[{\"line\":\"Good job, {PLAYER_NAME}!\",\"typewriter\":true},{\"line\":\"There are many kinds of trees in the wilderness\",\"typewriter\":true},{\"line\":\"Some extremly rare, some not so much\",\"typewriter\":true}]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"a5f75927-29ef-4571-a5e9-fcc898ac4f43\",\"content\":[{\"line\":\"Nevertheless, it\\u0027s an essential material\",\"typewriter\":true},{\"line\":\"For crafting bows, making campires and everything in between.\",\"typewriter\":true}]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"1e46b592-c072-4f49-9bc7-e65fac9f0ae3\",\"content\":[{\"line\":\"Now, go meet the §6survival guide.\",\"typewriter\":true},{\"line\":\"He will teach you how to not starve to death.\",\"typewriter\":true}],\"actions\":[\"teleport_player_{world_world}_{x_2493}_{y_128}_{z_2411}\",\"quest_step_finish_{stable_id_tutorial.lumberjack-bring-logs}\"]}]},\"falsyFollowup\":{\"id\":\"tutorial.survival-guide\",\"steps\":[{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"check.axe\",\"content\":[],\"conditions\":[\"inventory_contains_one_of_{item_id_1000}_{amount_1}\"],\"logicalOperator\":\"AND\",\"falsyFollowup\":{\"id\":\"tutorial.survival-guide\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"f5be871e-b243-479a-98ab-8d73af3d17ce\",\"content\":[{\"line\":\"What? Already lost the axe?\",\"typewriter\":true}],\"actions\":[\"give_item_{id_1000}_{amount_1}\"]}]}},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"struggling.block\",\"content\":[{\"line\":\"§63 logs,§f cowboy. Aim for the weak spot!\",\"typewriter\":true}]}]}}]}},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"10a33908-0289-4543-bb8f-3935e74c932e\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.lumberjack-talk-to}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial.survival-guide\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"8a9f1eef-acca-47b8-b106-90308eacc502\",\"content\":[{\"line\":\"Hey there!\",\"typewriter\":true},{\"line\":\"See that tree over there?\",\"typewriter\":true},{\"line\":\"That’s your first step to surviving out here.\",\"typewriter\":true}]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"fdf0d9cb-3018-42ca-b766-2a94de6aa232\",\"content\":[{\"line\":\"By themselves, logs won’t do you much good.\",\"typewriter\":true},{\"line\":\"But you can use them as a material, or start a fire\",\"typewriter\":true}]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"cab4125d-79b6-43e7-8586-9af8bd40177d\",\"content\":[{\"line\":\"Ok, enough of talking.\",\"typewriter\":true},{\"line\":\"My job is to teach you how to cut trees, that\\u0027s it\",\"typewriter\":true}]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"83ae92bb-945b-40b2-a6f2-77eb8f05800d\",\"content\":[{\"line\":\"Here, take this axe, and bring me §63 logs.\",\"typewriter\":true}],\"actions\":[\"give_item_{id_1000}_{amount_1}\",\"quest_step_finish_{stable_id_tutorial.lumberjack-talk-to}\"]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"b0eb609f-81af-42c2-847d-c5f1bdafd715\",\"content\":[{\"line\":\"Remember, you can’t just cut the tree anywhere.\",\"typewriter\":true},{\"line\":\"Chop at the §6weak point.\",\"typewriter\":true},{\"line\":\"The marker shows you where.\",\"typewriter\":true}]}]}}]}");
    public static final Supplier<DialogBuilder> tutorial_blacksmithDialog = () -> DialogBuilder.deserialize("{\"id\":\"tutorial-island.tutor-blacksmith\",\"steps\":[{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"4d89e089-3275-4763-8d13-bb65c7901195\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.blacksmith-talk-to}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial-island.tutor-blacksmith\",\"steps\":[{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"77c8b519-b651-4a91-89ff-9243d42fa108\",\"content\":[],\"conditions\":[\"inventory_contains_one_of_{item_id_1100}_{amount_1}\"],\"logicalOperator\":\"AND\",\"falsyFollowup\":{\"id\":\"tutorial-island.tutor-blacksmith\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"d9567dde-5bb3-42ee-b839-2b5284ab4696\",\"content\":[],\"actions\":[\"give_item_{id_1100}_{amount_1}\"],\"uuid\":\"534940fd-b860-4351-afc4-9401eab71edb\"}],\"uuid\":\"7062ed40-68ab-4cd3-b37a-f65a5497425e\"},\"uuid\":\"411ab5ad-1539-4fb1-b3a1-8fa891800ea1\"},{\"type\":\"InteractiveDialogStep\",\"uniqueIdentifier\":\"916d78e3-2d3d-407a-8224-81cc1cea83c3\",\"content\":[{\"line\":\"Hello, {PLAYER_NAME}! You are new around here?\",\"typewriter\":true}],\"options\":[{\"label\":\"Yes!\",\"actions\":[],\"followup\":{\"id\":\"tutorial-island.tutor-blacksmith\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"e788b6a4-3541-412f-bcef-9b51118566b4\",\"content\":[{\"line\":\"Great! Welcome to the §4Rune§6Heim, §f{PLAYER_NAME}!\",\"typewriter\":true},{\"line\":\"Many great adventures await!\",\"typewriter\":true}],\"uuid\":\"4b014ba0-7bb2-498f-9aff-2c3f5aaefa16\",\"actions\":[]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"eac6ca56-46f1-43e5-92e3-103513bb7113\",\"content\":[{\"line\":\"Unfortunately for you, you won't even get past the city gates!\",\"typewriter\":true},{\"line\":\"But you are lucky I am here, I'll help you out.\",\"typewriter\":true}],\"uuid\":\"5d145759-8525-41a2-93df-296852019cb5\",\"actions\":[]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"guide.start\",\"content\":[{\"line\":\"The wilderness is full of different creatures\",\"typewriter\":true},{\"line\":\"And without any §6gear§f You’ll end up as a goblin’s dinner.\",\"typewriter\":true}],\"uuid\":\"57c38538-6f13-4b4b-a7f1-9818ecc3f91d\",\"actions\":[]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"fa2bf496-b73b-46b9-a473-78a5f89ca907\",\"content\":[{\"line\":\"First of all you need to mine exactly §e1 copper ore and §e1 tin ore.\",\"typewriter\":true},{\"line\":\"Then, smelt them into a §ebronze bar.\",\"typewriter\":true}],\"uuid\":\"0eecf94c-9ea9-4b4e-9f68-f25fd4d8db8b\",\"actions\":[]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"a9074ef0-00f9-4f7a-a0fd-88453cee1966\",\"content\":[{\"line\":\"After that, use your hammer on an §eAnvil\",\"typewriter\":true},{\"line\":\"And select §eBronze Dagger\",\"typewriter\":true},{\"line\":\"Oh, I almost forgot! Take this hammer, too.\",\"typewriter\":true}],\"actions\":[\"give_item_{id_1}_{amount_1}\",\"quest_step_finish_{stable_id_tutorial.blacksmith-talk-to}\"],\"uuid\":\"e2a65ce1-ac82-49ae-8ea9-11c3c85c201f\"}],\"uuid\":\"e7f1a4fb-644b-4917-a475-a10c550342f1\"}},{\"label\":\"Uh, no\",\"actions\":[],\"followup\":{\"id\":\"tutorial-island.tutor-blacksmith\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"7e340949-3558-4bf4-b7cd-f00488caf3bc\",\"content\":[{\"line\":\"Trying to be funny, eh?\",\"typewriter\":true},{\"line\":\"I remember everyone whom I taught how to survive.\",\"typewriter\":true}],\"uuid\":\"a397cd7d-182e-4bcc-b441-784b17e5386b\",\"actions\":[]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"95adfd2e-3a52-4a89-951c-afea8df217b5\",\"content\":[{\"line\":\"You won't get even past the city gates!\",\"typewriter\":true}],\"uuid\":\"9bf48c53-b09f-480a-8ba4-df83abe8410e\",\"actions\":[]},{\"type\":\"TeleportBranchStep\",\"uniqueIdentifier\":\"be490770-c9e7-4ecd-ad34-bad6a93d63e7\",\"teleportToUniqueIdentifier\":\"guide.start\",\"content\":[],\"uuid\":\"d2e5a063-de08-4a80-82eb-08dde0d36a87\",\"actions\":[]}],\"uuid\":\"aa7b428d-3b7e-46a6-b22d-e87d2edb8c09\"}}],\"uuid\":\"e08b2d44-6c99-4bc0-863d-c0acdb008570\"}],\"uuid\":\"77582dde-d101-4b84-974c-120789c7a5c9\"},\"uuid\":\"f2dfb919-fc62-4289-b4a0-c5df32ba7e03\"},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"9a7bf524-282b-4573-8a35-da46b8f80992\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.blacksmith-bring-dagger}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial-island.tutor-blacksmith\",\"steps\":[{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"pickaxe.check\",\"content\":[],\"conditions\":[\"inventory_contains_one_of_{item_id_1100}_{amount_1}\"],\"logicalOperator\":\"AND\",\"falsyFollowup\":{\"id\":\"tutorial-island.tutor-blacksmith\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"b1f98f40-5cf3-422b-9a4c-718c73da7dd1\",\"content\":[{\"line\":\"If you gonna lose your tools like this, we are getting nowhere!\",\"typewriter\":true}],\"actions\":[\"give_item_{id_1100}_{amount_1}\"],\"uuid\":\"6c8ecb02-e26e-428e-8972-820b7824b8cf\"}],\"uuid\":\"1fc1927d-6e0b-428c-a7e7-133c2935661f\"},\"uuid\":\"172482b7-563f-4958-9129-3c749349fd3a\"},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"hammer.check\",\"content\":[],\"conditions\":[\"inventory_contains_one_of_{item_id_1}_{amount_1}\"],\"logicalOperator\":\"AND\",\"falsyFollowup\":{\"id\":\"tutorial-island.tutor-blacksmith\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"0f467344-1d65-41b2-bde1-7619fbab37f3\",\"content\":[{\"line\":\"Where is your hammer, rookie?\",\"typewriter\":true}],\"actions\":[\"give_item_{id_1}_{amount_1}\"],\"uuid\":\"b8859764-abdd-487f-ba46-8ba1606a0918\"}],\"uuid\":\"496c3480-ed2e-4d71-99e0-751851b233cb\"},\"uuid\":\"922a77e4-a158-4c7e-9144-5cf016f8f5f5\"},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"dagger.check\",\"content\":[],\"conditions\":[\"inventory_contains_one_of_{item_id_800}_{amount_1}\"],\"logicalOperator\":\"AND\",\"truthyFollowup\":{\"id\":\"tutorial-island.tutor-blacksmith\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"3a47a384-1561-41fc-9422-3e6204135f92\",\"content\":[{\"line\":\"Good job, {PLAYER_NAME}!\",\"typewriter\":true},{\"line\":\"Remember, this is just the basics\",\"typewriter\":true}],\"uuid\":\"f1fe1e8a-5ecd-4632-8e94-087e6c83e13c\",\"actions\":[]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"e2c09661-f0d8-4b5c-964f-fdb01ac692e0\",\"content\":[{\"line\":\"Keep mastering your skills\",\"typewriter\":true},{\"line\":\"Explore the world to find new materials\",\"typewriter\":true},{\"line\":\"And then §4Rune§eHeim §fwill finally get the hero it deserves\",\"typewriter\":true}],\"actions\":[\"teleport_player_{world_world}_{x_2510}_{y_103}_{z_2410}\",\"quest_step_finish_{stable_id_tutorial.blacksmith-bring-dagger}\"],\"uuid\":\"243bc396-7da9-49bb-88ea-9d49e24801fc\"}],\"uuid\":\"71719d26-b0b3-4ece-9c04-2e324f1864b7\"},\"falsyFollowup\":{\"id\":\"tutorial-island.tutor-blacksmith\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"struggling.block\",\"content\":[{\"line\":\"Huh, struggling already?\",\"typewriter\":true},{\"line\":\"Remember, you need §e1 copper ore§f and §e1 tin ore\",\"typewriter\":true},{\"line\":\"Then smelt it into a §ebronze bar\",\"typewriter\":true}],\"uuid\":\"50cf3e5a-d60c-442c-82d3-dec1764109d2\",\"actions\":[]},{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"8b8270e4-f8bd-47f2-83e9-ac60d94a0b28\",\"content\":[{\"line\":\"After that, use your §ehammer§f on an §eanvil\",\"typewriter\":true},{\"line\":\"And bring me §e1 Bronze Dagger\",\"typewriter\":true}],\"uuid\":\"4282911f-680c-4a31-9831-c4bd4eb9c143\",\"actions\":[]}],\"uuid\":\"9e33e6cf-b1d1-4888-a6ec-dd7f0f825d22\"},\"uuid\":\"4ee18240-d8ac-4485-a896-c1273b3759c4\"}],\"uuid\":\"8ecf596f-b3aa-4888-8dbc-9fad4442df06\"},\"uuid\":\"7862b8d6-80e6-48e4-b670-b899311270ce\"},{\"type\":\"ConditionStep\",\"uniqueIdentifier\":\"21e4ad50-ef0a-48e0-892f-28a2180ae0e2\",\"content\":[],\"conditions\":[\"quest_step_active_{stable_id_tutorial.blacksmith-collect-ores}\",\"quest_step_active_{stable_id_tutorial.blacksmith-smelt-bar}\",\"quest_step_active_{stable_id_tutorial.blacksmith-smith-dagger}\"],\"logicalOperator\":\"OR\",\"truthyFollowup\":{\"id\":\"tutorial-island.tutor-blacksmith\",\"steps\":[{\"type\":\"TeleportBranchStep\",\"uniqueIdentifier\":\"d37cf98e-5cd2-468b-bfcf-09f689532b4f\",\"teleportToUniqueIdentifier\":\"struggling.block\",\"content\":[],\"uuid\":\"730456a3-10bd-4b78-a169-a1136ad69bb8\",\"actions\":[]}],\"uuid\":\"ad64bc33-1316-4119-9135-5e3ee97fe7e7\"},\"uuid\":\"a835a471-c213-4458-9168-56d17c1b7b4c\"}],\"uuid\":\"55530a64-1ac7-4435-b24d-ad8f29e8a6b8\"}");
    public static final Supplier<DialogBuilder> drakkarBlacksmithDialog = () -> DialogBuilder.deserialize("{\"id\":\"generic-shop-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"e5693b53-abeb-4f13-a9b7-d660c8995046\",\"content\":[{\"line\":\"Hello there!\",\"typewriter\":true}],\"uuid\":\"98cbdf67-69a5-43d9-bfb4-57ed85e09702\",\"actions\":[]},{\"uuid\":\"573cc3f7-8d77-48a3-9b98-de70df720689\",\"uniqueIdentifier\":\"fca3057c-9348-4979-8bff-31037445ae0a\",\"type\":\"ConditionStep\",\"conditions\":[\"quest_step_active_{stable_id_blacksmith-right-hand-i.acquire-sapphire}\",\"quest_step_active_{stable_id_blacksmith-right-hand-i.bring-sapphire}\"],\"id\":\"generic-shop-dialog\",\"logicalOperator\":\"OR\",\"truthyFollowup\":{\"uuid\":\"1a88b330-bd3f-49bf-a0e1-062a12c48536\",\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"2a143069-1f61-4663-b3c0-68a5bb6b91a9\",\"uniqueIdentifier\":\"17868159-14f6-40e5-88d8-fc913a696822\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Have you found the sapphire yet?\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"073c68af-f4d7-426a-8196-2c9e9af5bc9a\",\"uniqueIdentifier\":\"e042dcc6-3f0f-4eee-9146-9ed01bf308be\",\"type\":\"ConditionStep\",\"conditions\":[\"quest_step_active_{stable_id_blacksmith-right-hand-i.bring-sapphire}\"],\"id\":\"generic-shop-dialog\",\"truthyFollowup\":{\"uuid\":\"aa641c48-1e32-4392-919a-9a56af9c02bd\",\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"76729da1-ca5d-4953-a856-ad5c55914548\",\"uniqueIdentifier\":\"ca26dea5-7be2-441d-b360-6d3517dd89fd\",\"type\":\"ConditionStep\",\"conditions\":[\"remove_inventory_item_successful_{item_id_2800}_{amount_1}\"],\"id\":\"generic-shop-dialog\",\"truthyFollowup\":{\"uuid\":\"be55da60-b254-40bc-92c0-2c7e41bf5f90\",\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"3b61e5b2-2fbc-4c7d-878a-a14d3a9e7d75\",\"uniqueIdentifier\":\"27bdea20-2837-4639-b0e0-25bc3447b001\",\"type\":\"PassiveDialogStep\",\"content\":[],\"id\":\"generic-shop-dialog\",\"actions\":[\"quest_step_finish_{stable_id_blacksmith-right-hand-i.bring-sapphire}\"]},{\"uuid\":\"8b9048f7-c908-45a6-8847-8197b11af5db\",\"uniqueIdentifier\":\"94733771-bba0-4021-85ce-1ec51ebf47ee\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Thank you so much, {PLAYER_NAME}!\",\"typewriter\":true},{\"line\":\"You are the real HERO!\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"0fd2d5ea-2822-486e-a044-0bab0201b347\",\"uniqueIdentifier\":\"06bbc551-fbe1-4481-8561-819a717f7c80\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Here, take this\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"b8043f30-01e5-4d3c-9fc9-b24694b5663c\",\"uniqueIdentifier\":\"e069dcb2-3c19-4d31-8836-f44aac9c9567\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"It's not much, but you have done a good deed.\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"862d6d37-1c0e-4566-87b9-2e4ba95991cc\",\"uniqueIdentifier\":\"38e6d38a-e9d1-4c25-9707-99c7697c6342\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"void\",\"id\":\"generic-shop-dialog\"}]},\"falsyFollowup\":{\"uuid\":\"7543b3f3-60be-4cf6-985e-31e8af88dd58\",\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"3aebaf46-f403-4871-a26f-f34f5ab36235\",\"uniqueIdentifier\":\"bad7b80e-fe84-4045-ba00-dd6d801ebc00\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"or.else\",\"id\":\"generic-shop-dialog\"}]}}]},\"falsyFollowup\":{\"uuid\":\"1ea1ecbe-db55-4d2c-80a5-1937d7f1fd19\",\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"1db5bd6e-a979-41c2-831a-ca78d72bcccb\",\"uniqueIdentifier\":\"or.else\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Or else... *sobs*\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"ed1b92fd-33f5-47f5-ab00-0cfc0de829d4\",\"uniqueIdentifier\":\"9fb950fd-6c4d-420c-b7a7-af6e7ca8971f\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"void\",\"id\":\"generic-shop-dialog\"}]}}]}},{\"uuid\":\"379a3fa1-51b0-43ef-80ed-d6a3393f85c8\",\"uniqueIdentifier\":\"c35fe370-3542-46de-9943-263ee29e61e9\",\"type\":\"ConditionStep\",\"conditions\":[\"quest_step_active_{stable_id_blacksmith-right-hand-i.gave-in-res}\"],\"id\":\"generic-shop-dialog\",\"truthyFollowup\":{\"uuid\":\"809f0348-7017-4270-bc26-e0987175efeb\",\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"f98a2e96-493a-4668-adcc-c00a74e149a0\",\"uniqueIdentifier\":\"7236dd2e-2191-456a-aa4e-59168451f9b2\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"sapphire.countination\",\"id\":\"generic-shop-dialog\"}]}},{\"uuid\":\"4e4517ac-0dd2-4f0e-b59c-b73d25964fb6\",\"uniqueIdentifier\":\"d148645c-25f9-4ce2-92f0-a91e829cc43c\",\"type\":\"ConditionStep\",\"conditions\":[\"quest_step_active_{stable_id_blacksmith-right-hand-i.collect-ores}\",\"quest_step_active_{stable_id_blacksmith-right-hand-i.smelt-ores}\",\"quest_step_active_{stable_id_blacksmith-right-hand-i.bring-ores}\"],\"id\":\"generic-shop-dialog\",\"logicalOperator\":\"OR\",\"truthyFollowup\":{\"uuid\":\"e961f16e-c48f-4ea6-aa92-fd0dfa588029\",\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"89e0b3f8-1d4e-48f1-a1a6-0d1249df7714\",\"uniqueIdentifier\":\"43610008-88e9-4c37-8cd6-1d8f0ace1ab7\",\"type\":\"InteractiveDialogStep\",\"options\":[{\"label\":\"Yeah\",\"actions\":[],\"followup\":{\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"bd978b8e-71b6-4eaa-9bee-334edc85d31d\",\"uniqueIdentifier\":\"286ff01c-38fd-459d-90ae-c52186d41dea\",\"type\":\"ConditionStep\",\"conditions\":[\"remove_inventory_item_successful_{item_id_200}_{amount_16}\"],\"id\":\"generic-shop-dialog\",\"falsyFollowup\":{\"uuid\":\"92582726-3236-4f1c-b42d-c832a3820f42\",\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"8d0f5acd-5267-49b1-a120-ad5341676b56\",\"uniqueIdentifier\":\"b1179f80-cc0a-458f-bc5e-e461c18c3b99\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Please give them to me!\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"8c8d84ca-39a0-407c-968c-dd5f55631a8e\",\"uniqueIdentifier\":\"9718784b-2c16-48c4-8d6f-1d0b9a618cba\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"void\",\"id\":\"generic-shop-dialog\"}]},\"truthyFollowup\":{\"uuid\":\"ec60103d-1e96-459b-a1f4-00d47eecbca5\",\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"6f208ff8-7d98-48b5-bacc-db52f974f7dc\",\"uniqueIdentifier\":\"f8ab4a09-f023-420b-80db-f085c8741a53\",\"type\":\"PassiveDialogStep\",\"content\":[],\"id\":\"generic-shop-dialog\",\"actions\":[\"quest_step_finish_{stable_id_blacksmith-right-hand-i.bring-ores}\"]},{\"uuid\":\"799dd3f5-1b14-4d29-be80-b3e20d7ef72c\",\"uniqueIdentifier\":\"sapphire.countination\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Oh my god! Thanks so much, {PLAYER_NAME}!\",\"typewriter\":true},{\"line\":\"This is just enough to finish the statue!\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"3ff14681-6eb1-4dae-8334-39b8ebcc61ed\",\"uniqueIdentifier\":\"698dd0ec-ea66-4462-b208-cbdae69befc4\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"*smile leaves his face.*\",\"typewriter\":false},{\"line\":\"B..but I have got another request...\",\"typewriter\":true},{\"line\":\"from the royal family...\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"00f46646-d7cb-4834-828b-c715b4895e61\",\"uniqueIdentifier\":\"1244b7c4-9733-4295-9ad1-a165a68eb0ac\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"They want a gem stone embedded into the statue\",\"typewriter\":true},{\"line\":\"A sapphire...\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"9c79add2-0e89-4348-853e-e72d47fccb26\",\"uniqueIdentifier\":\"29a9729c-be17-4399-994b-3459b85af66e\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"But I'm not a jeweler! I don't have any gems!\",\"typewriter\":true},{\"line\":\"Think you can find §61x Uncut Sapphire?\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"8bacb2ad-a29f-45bf-bccd-e00a3efc7ac0\",\"uniqueIdentifier\":\"62e3e3dd-6fd4-4b3a-ada7-91624d858ba3\",\"type\":\"InteractiveDialogStep\",\"options\":[{\"label\":\"Ill get you one\",\"actions\":[\"quest_step_finish_{stable_id_blacksmith-right-hand-i.gave-in-res}\"],\"followup\":{\"id\":\"generic-shop-dialog\",\"steps\":[],\"uuid\":\"04c30f81-e63f-4b8f-9a6a-7da79db3e7b8\"}}],\"content\":[{\"line\":\"Maybe any merchants selling one?\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"439cb79d-5edc-4c78-9a74-d7793f42f020\",\"uniqueIdentifier\":\"aeb30114-7d20-47f2-986c-c0a9bc569dc9\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"void\",\"id\":\"generic-shop-dialog\"}]}}],\"uuid\":\"f48deaf9-9786-4701-b598-51800c9c4c52\"}},{\"label\":\"No, not yet\",\"actions\":[],\"followup\":{\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"69cfe2c7-26db-4a66-83b8-d3b9590781c2\",\"uniqueIdentifier\":\"d6f23e5e-305d-42d8-99ca-e6e6d96eacb0\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Please hurry up!\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"36723596-da0a-4ff7-98df-ce6eb04e2bd3\",\"uniqueIdentifier\":\"122db0ae-10a4-49ad-9d10-bd9d259ea804\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"void\",\"id\":\"generic-shop-dialog\"}],\"uuid\":\"956d69aa-1fee-415f-a161-81367a61e9b8\"}}],\"content\":[{\"line\":\"Have you brought the bronze bars, {PLAYER_NAME}?\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"}]}},{\"uuid\":\"d92ad88c-17ef-427e-abde-9b7b238e15d5\",\"uniqueIdentifier\":\"83f98780-50ce-432e-ab2d-a74f295b6b75\",\"type\":\"ConditionStep\",\"conditions\":[\"quest_finished_{id_blacksmiths-right-hand-i}\"],\"id\":\"generic-shop-dialog\",\"falsyFollowup\":{\"uuid\":\"6cb44323-027e-47d7-99f5-20caa12310bf\",\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"965bcc5b-2050-4bfe-b9fd-5fbcde78c3fc\",\"uniqueIdentifier\":\"845b06db-b68e-4ede-8e33-4c74db214d44\",\"type\":\"ConditionStep\",\"conditions\":[\"quest_started_{id_blacksmiths-right-hand-i}\"],\"id\":\"generic-shop-dialog\",\"falsyFollowup\":{\"uuid\":\"8ec0d66e-81f2-4cba-a32a-9b9debca1750\",\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"fc6ca2de-3cc4-4b05-8030-d1b707d6f5b2\",\"uniqueIdentifier\":\"2c81ddd2-202d-48c3-a07f-b1be16c26b01\",\"type\":\"InteractiveDialogStep\",\"options\":[{\"label\":\"Are you ok?\",\"actions\":[],\"followup\":{\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"e2a4815c-fcf0-41f0-b0f9-7396b75fa148\",\"uniqueIdentifier\":\"e4acad65-7a98-4b15-b4e7-45c1d5d57be0\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"No! I'm not okay\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"debff778-fe31-41eb-9cfa-b00eaa78f251\",\"uniqueIdentifier\":\"40b478fc-2044-4c13-a217-f5177c97c02c\",\"type\":\"InteractiveDialogStep\",\"options\":[{\"label\":\"What happened?\",\"actions\":[],\"followup\":{\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"400a181d-993a-4bdc-b521-e708f8d46307\",\"uniqueIdentifier\":\"4706d67c-d068-404c-bcdd-9b21c999f862\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"The royal family ordered me to make\",\"typewriter\":true},{\"line\":\"A bronze statue for the yearly carnival\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"f6be4754-6e2c-4d11-a578-d0504d669e3b\",\"uniqueIdentifier\":\"49b1db6d-858e-43a0-9f00-59a3b388d3c3\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"But... This merchant from Valdrin\",\"typewriter\":true},{\"line\":\"He scammed me! He took the money...\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"6e6ef9dd-8be6-40b6-aa44-f32837edb543\",\"uniqueIdentifier\":\"77d8c643-d770-48c5-92bb-7542aafba8f3\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"And shipped me the wrong grade bronze bars!\",\"typewriter\":true},{\"line\":\"They look absolutely disgusting!\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"6c76e09f-561a-4a52-a25e-73db8e29d736\",\"uniqueIdentifier\":\"066b6819-04df-4ac0-966e-d87622c77c92\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Now I don't have enough of material\",\"typewriter\":true},{\"line\":\"To finish this statue!\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"50f0da5b-d014-4501-af24-c8f2bcbc7815\",\"uniqueIdentifier\":\"b69fb632-be2d-459a-8eae-8365dbf50df7\",\"type\":\"InteractiveDialogStep\",\"options\":[{\"label\":\"Could I help you?\",\"actions\":[],\"followup\":{\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"db6e7380-1a20-4328-a2aa-1cd49c58e50e\",\"uniqueIdentifier\":\"e2ac90ae-5333-47c2-ba91-f95e162b48bd\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Oh my god, yes!\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"fa73d402-01bf-43ca-9a43-b2dae39b8032\",\"uniqueIdentifier\":\"999e79d2-ee75-4188-900d-5fb62dec7cae\",\"type\":\"InteractiveDialogStep\",\"options\":[{\"label\":\"Sure, I'll bring them\",\"actions\":[],\"followup\":{\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"523c9918-c45c-4ad7-92e1-8e1daf68cfc4\",\"uniqueIdentifier\":\"4e0e0557-6ecf-4d04-880d-8ac59d99c39f\",\"type\":\"ConditionStep\",\"conditions\":[\"start_quest_if_requirements_met_{id_blacksmiths-right-hand-i}\"],\"id\":\"generic-shop-dialog\",\"truthyFollowup\":{\"uuid\":\"d64646eb-77ff-4426-86ac-d4f4b535480a\",\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"57b4d825-b1b7-4187-81e7-13db8e542cf4\",\"uniqueIdentifier\":\"de24d420-5c67-4c8c-8e24-f5c857bbe72e\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Thank you!! I'll keep working on the statue!\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"852ba863-b24d-4905-a938-666d610cb10f\",\"uniqueIdentifier\":\"096b0041-c5b4-4304-92f7-17d3d7c45391\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"void\",\"id\":\"generic-shop-dialog\"}]}}],\"uuid\":\"c56f00f8-1d6a-4a38-8fd5-db78d3989919\"}},{\"label\":\"No thanks\",\"actions\":[],\"followup\":{\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"a188ae1f-680c-42cf-a9e6-99ef9ee98b59\",\"uniqueIdentifier\":\"a6c09efc-2701-4c25-a1de-9f8c9248dafc\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"void\",\"id\":\"generic-shop-dialog\"}],\"uuid\":\"f7bf0f3f-09e7-4eca-8eb5-a622497d18f6\"}}],\"content\":[{\"line\":\"I need §616x Bronze Bar§f. Think you can get those?\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"}],\"uuid\":\"68474360-7d88-4a60-934e-1bdd92c89bab\"}},{\"label\":\"Sucks to be you\",\"actions\":[],\"followup\":{\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"bfdc821d-fc5b-4c6d-ae23-2c2675bb6cdf\",\"uniqueIdentifier\":\"09dde742-320f-40ed-8f29-7bbca74b590c\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Leave me alone!\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"7976d384-fb21-470f-a78d-0cd4e01ea677\",\"uniqueIdentifier\":\"65781bd7-481d-4e7c-a69f-7b5ebd9d7bde\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"void\",\"id\":\"generic-shop-dialog\"}],\"uuid\":\"7f6ac00f-aaee-4324-a91a-821594f21665\"}}],\"content\":[{\"line\":\"I'll get executed...\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"}],\"uuid\":\"544bf116-1b73-4a56-8342-17a664b5aae9\"}},{\"label\":\"Doesn't sound interesting\",\"actions\":[],\"followup\":{\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"752b1145-f367-49a2-a93a-3e1acd0882ad\",\"uniqueIdentifier\":\"eb4efd63-474d-4244-b3b7-0485c95fa5e2\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"farewell\",\"id\":\"generic-shop-dialog\"}],\"uuid\":\"bd29651c-c605-4f84-a5da-6f84fd7c774a\"}}],\"content\":[{\"line\":\"I'm a dead man walking...\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"}],\"uuid\":\"5bd5b29a-aae7-4416-acbb-0da458f9955d\"}},{\"label\":\"Do you sell something?\",\"actions\":[],\"followup\":{\"id\":\"generic-shop-dialog\",\"steps\":[{\"uuid\":\"038e4be0-8818-46fb-b8bd-09d71a9781b6\",\"uniqueIdentifier\":\"c33a6cd8-4989-4e97-aba0-2dcd4f8e127a\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Oh. Yeah...\",\"typewriter\":true}],\"id\":\"generic-shop-dialog\"},{\"uuid\":\"c2fbee88-440f-486f-9cdf-9638b83be448\",\"uniqueIdentifier\":\"09f5e909-3c17-48fa-a6df-3f855c523ed5\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"offer.wares\",\"id\":\"generic-shop-dialog\"}],\"uuid\":\"409b9100-7d6c-459a-82a2-6abdb0cca723\"}}],\"content\":[{\"line\":\"*looks stressed*\",\"typewriter\":false}],\"id\":\"generic-shop-dialog\"}]}}]}},{\"type\":\"InteractiveDialogStep\",\"uniqueIdentifier\":\"offer.wares\",\"content\":[{\"line\":\"Interested in my wares?\",\"typewriter\":true}],\"options\":[{\"label\":\"§aYes §fⓩ\",\"actions\":[\"dialog_holder_forward_interaction\"],\"followup\":{\"id\":\"followup-empty\",\"steps\":[],\"uuid\":\"3f353511-dfb0-4232-9c8d-1fa016ccb74c\"}},{\"label\":\"§cNo\",\"actions\":[],\"followup\":{\"id\":\"generic-shop-dialog\",\"steps\":[{\"type\":\"PassiveDialogStep\",\"uniqueIdentifier\":\"farewell\",\"content\":[{\"line\":\"Take care!\",\"typewriter\":true}],\"uuid\":\"406d80e5-fd15-47d4-8805-660d1d2d9171\",\"actions\":[]}],\"uuid\":\"42231cf7-9cbf-4287-b25a-34e22198f112\"}}],\"uuid\":\"ae0a35ae-3fa1-4696-8be1-4e7ab2b02d9b\"},{\"uuid\":\"d68c0244-1e5d-4979-bb08-e650b5b7fce5\",\"uniqueIdentifier\":\"void\",\"type\":\"ConditionStep\",\"conditions\":[\"never_true\"],\"id\":\"generic-shop-dialog\"}],\"uuid\":\"0bb7dde1-e664-466c-bd0c-44c1374b4c30\"}");
    public static final Supplier<DialogBuilder> drakkarCoachmanDialog = () -> DialogBuilder.deserialize("{\"uuid\":\"708e0d23-1f31-4d84-9bd1-47c014dd02ef\",\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"0ce9fb04-e462-4b1c-a985-1486a595f6a9\",\"uniqueIdentifier\":\"715c6fe4-ae73-4721-9359-674761eae603\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Greetings, traveler!\",\"typewriter\":true},{\"line\":\"You look like you’ve walked a long road.\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"},{\"uuid\":\"35492738-6d09-4a4f-81e2-cce1cd78d8e9\",\"uniqueIdentifier\":\"5e3d9e67-f851-4e26-a3ec-ee16be10a054\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"I drive folk safely between towns and villages\",\"typewriter\":true},{\"line\":\"Faster than your boots could ever carry you.\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"},{\"uuid\":\"8725527c-8f22-4784-bd5a-d63fffff03fb\",\"uniqueIdentifier\":\"587f7ce3-539a-4fca-9e50-5ddec8134bf0\",\"type\":\"InteractiveDialogStep\",\"options\":[{\"label\":\"Drakkars Mine §6(30 coins)\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"35f43f4e-0cb4-40e8-90b0-3b25a4e2a939\",\"uniqueIdentifier\":\"d23054bd-cb2d-4b7f-b790-137b887fad50\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"A mine, heh?\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"},{\"uuid\":\"7f438105-22a5-40c3-8b26-2e60b3967ea0\",\"uniqueIdentifier\":\"b9e65d7c-00e2-4929-a928-6f38809f7922\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Sure, but you will have to walk back!\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"},{\"uuid\":\"547a3314-11b4-4a50-8a03-72456ffa0764\",\"uniqueIdentifier\":\"a2fc9ad1-b378-4e04-a880-1be735e51e92\",\"type\":\"InteractiveDialogStep\",\"options\":[{\"label\":\"Yes!\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"403c767c-9920-476f-bf62-b475e8832914\",\"uniqueIdentifier\":\"a194f365-14ad-46e8-adb6-4b279f69cde2\",\"type\":\"ConditionStep\",\"conditions\":[\"remove_pouch_coins_successful_{amount_30}\"],\"id\":\"coachman-drakkar\",\"truthyFollowup\":{\"uuid\":\"21598c67-e9d5-4492-a8cb-64bb77b8ab50\",\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"b21447b4-1701-4527-8c52-1fa1408df709\",\"uniqueIdentifier\":\"3a6863c6-aff8-4967-a755-c75ba9ce1c30\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Jump in!\",\"typewriter\":true}],\"id\":\"coachman-drakkar\",\"actions\":[\"travel_sequence_{duration_seconds_8}_{world_world}_{dest_x_29}_{dest_y_91}_{dest_z_93}_{dest_yaw_0}_{buffer_x_3250}_{buffer_y_10}_{buffer_z_3249}_{buffer_yaw_-90}\"]},{\"uuid\":\"037ad754-295c-4f54-bf48-49588b8e1198\",\"uniqueIdentifier\":\"5341d136-48cc-4c3b-b82a-780a464171e5\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"void\",\"id\":\"coachman-drakkar\"}]},\"falsyFollowup\":{\"uuid\":\"f366cfb3-11ae-43ee-9533-b00e40b64b3d\",\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"2f1d8b7d-9176-41c9-b534-49d3971686d6\",\"uniqueIdentifier\":\"7218e7c6-b55d-4052-bea3-4e1006db2b79\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"no.money\",\"id\":\"coachman-drakkar\"}]}}],\"uuid\":\"e902e228-ea41-4ab2-a243-140d290f8931\"}},{\"label\":\"Nevermind\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"3fc2a5ad-fa46-43ec-9e4e-fd32903969da\",\"uniqueIdentifier\":\"5fc03246-50a2-4b83-a6f3-95a70045e0b3\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"farewell\",\"id\":\"coachman-drakkar\"}],\"uuid\":\"ff5df369-e3a7-4aa4-9861-4844ba73b2f0\"}}],\"content\":[{\"line\":\"Are you ready?\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"}],\"uuid\":\"7f80548a-0670-4446-b3e0-47367d1d18e5\"}},{\"label\":\"Ashwale §6(25 coins)\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"a3600a4e-c2b8-487d-8577-fd4f53379ed0\",\"uniqueIdentifier\":\"c191e3ff-53a8-413f-967b-85348eab6481\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Ah, the Ashwale\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"},{\"uuid\":\"788c5ac0-9c52-4d0e-b8bb-8ae88c0af554\",\"uniqueIdentifier\":\"ba320622-06d3-4876-b801-c3f609971315\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Sure, but you will have to walk back!\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"},{\"uuid\":\"ab82719a-4631-414c-89c5-92ba4b812e34\",\"uniqueIdentifier\":\"3895368b-438b-4a9d-9044-61956450012e\",\"type\":\"InteractiveDialogStep\",\"options\":[{\"label\":\"Let's go!\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"8d9c684f-8128-4f64-b4c2-c19003e2d44b\",\"uniqueIdentifier\":\"80e4ad05-d315-4ec0-aa13-d8c0165f8916\",\"type\":\"ConditionStep\",\"conditions\":[\"remove_pouch_coins_successful_{amount_25}\"],\"id\":\"coachman-drakkar\",\"truthyFollowup\":{\"uuid\":\"48dbe527-539f-4895-8036-0b3089a24fc9\",\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"4942690d-547b-47a0-a85a-73c36778f6b8\",\"uniqueIdentifier\":\"a4a4cd73-6aee-421b-b0d0-fb89d552034d\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Jump in!\",\"typewriter\":true}],\"id\":\"coachman-drakkar\",\"actions\":[\"travel_sequence_{duration_seconds_6}_{world_world}_{dest_x_-22}_{dest_y_67}_{dest_z_-109}_{dest_yaw_90}_{buffer_x_3250}_{buffer_y_10}_{buffer_z_3249}_{buffer_yaw_-90}\"]},{\"uuid\":\"31a7fa9f-bb9a-4278-a378-667906a3a1a5\",\"uniqueIdentifier\":\"5c870cde-d50e-4bcb-a139-bc04ac7e80fc\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"void\",\"id\":\"coachman-drakkar\"}]},\"falsyFollowup\":{\"uuid\":\"75055a0a-2d51-41f4-a4e0-05c4d1d55bc5\",\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"ce8733ac-5eef-4ccc-94c9-86d5c520d53d\",\"uniqueIdentifier\":\"9def2c8e-3b19-4d20-9b12-167579a4ff09\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"no.money\",\"id\":\"coachman-drakkar\"}]}}],\"uuid\":\"6076790f-ac50-4ef7-a46a-c95957c29123\"}},{\"label\":\"Nevermind\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"99d7aebc-f8dc-4ddc-8065-aa68b165ad34\",\"uniqueIdentifier\":\"d4b3e336-8e59-488e-bfe1-268571927310\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"farewell\",\"id\":\"coachman-drakkar\"}],\"uuid\":\"e66b7c02-db4d-4731-adf1-71206c657b3c\"}}],\"content\":[{\"line\":\"Are ya ready?\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"}],\"uuid\":\"0aa6b8f6-6ecd-41bc-8808-750c10c4e57f\"}},{\"label\":\"Amber Farm §6(20 coins)\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"8abc8bd1-b8e9-4836-913e-4c66cba93d9e\",\"uniqueIdentifier\":\"3d1e734c-f2ca-4ba6-a455-6b6a29950a04\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Looking to grab some meat?\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"},{\"uuid\":\"eb15d259-4948-4cf6-a648-9aad634c8257\",\"uniqueIdentifier\":\"5616a245-a6bf-4327-a0c1-aa83bc085f94\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Sure, but you will have to walk back!\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"},{\"uuid\":\"5a723573-abdc-4628-bf48-816d783bef47\",\"uniqueIdentifier\":\"854cd28d-a144-421d-967e-a1da48d09c46\",\"type\":\"InteractiveDialogStep\",\"options\":[{\"label\":\"Let's go!\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"3f26eee9-249f-491b-8b56-a44051f0039e\",\"uniqueIdentifier\":\"be558343-e1bd-4db1-aa82-fd2a769385a7\",\"type\":\"ConditionStep\",\"conditions\":[\"remove_pouch_coins_successful_{amount_20}\"],\"id\":\"coachman-drakkar\",\"truthyFollowup\":{\"uuid\":\"ab8932ed-9c88-4497-abbd-79cb32f9227a\",\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"d9230133-4aa5-425e-9b47-ad9c5f1c1467\",\"uniqueIdentifier\":\"feea03f8-8fec-4cd8-bf14-4e3277c20d0a\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Hop inside!\",\"typewriter\":true}],\"id\":\"coachman-drakkar\",\"actions\":[\"travel_sequence_{duration_seconds_5}_{world_world}_{dest_x_243}_{dest_y_68}_{dest_z_-253}_{dest_yaw_-180}_{buffer_x_3250}_{buffer_y_10}_{buffer_z_3249}_{buffer_yaw_-90}\"]},{\"uuid\":\"5064f27d-f366-4e0a-b84d-aecd63532553\",\"uniqueIdentifier\":\"6d3e923e-f952-45c9-89a1-556b39f4781c\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"void\",\"id\":\"coachman-drakkar\"}]},\"falsyFollowup\":{\"uuid\":\"9d7582b4-e584-4da8-a376-630a522c7c2d\",\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"87fc6fc9-ec03-4ee5-83f0-3f27137de311\",\"uniqueIdentifier\":\"bae4a1e9-2d71-4e44-872f-1d3461f91528\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"no.money\",\"id\":\"coachman-drakkar\"}]}}],\"uuid\":\"b0ba2297-4e63-4164-8cae-9b21e6eb7d75\"}},{\"label\":\"Nevermind\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"2ce3d385-b49f-480b-b0c3-e1e7046a5185\",\"uniqueIdentifier\":\"1f242ccc-e693-47d3-bf94-54aa4730d4eb\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"farewell\",\"id\":\"coachman-drakkar\"}],\"uuid\":\"997b6e48-311d-4cde-88aa-1c9693a61e89\"}}],\"content\":[{\"line\":\"Ready?\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"}],\"uuid\":\"f8227e2c-ecbe-4d17-b3cf-756d25c8ae08\"}},{\"label\":\"Oakshade §6(40 coins)\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"ea358d4f-a657-4216-b24b-002ad382f416\",\"uniqueIdentifier\":\"b76b3473-f828-4046-8f00-a9788a677004\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Sure thing!\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"},{\"uuid\":\"4e87ec49-f3d7-468a-b8c4-00967dc04613\",\"uniqueIdentifier\":\"85e90ccd-8e20-4cdb-9758-5ff5606d9b2b\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"But you will have to walk back!\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"},{\"uuid\":\"bf854e36-9f58-4c5a-8ee9-948525e272c4\",\"uniqueIdentifier\":\"348bc899-cbcd-4780-894e-87f1a3210a6a\",\"type\":\"InteractiveDialogStep\",\"options\":[{\"label\":\"Let's go!\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"b3491e1d-4570-4195-a258-519458626018\",\"uniqueIdentifier\":\"d36f8372-ff65-4b2f-97f4-1815a7fc1592\",\"type\":\"ConditionStep\",\"conditions\":[\"remove_pouch_coins_successful_{amount_40}\"],\"id\":\"coachman-drakkar\",\"truthyFollowup\":{\"uuid\":\"6e51098e-616b-427f-b95d-c71d1eca4d32\",\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"d277495b-b675-45ae-b8e2-fa0ff8faa78c\",\"uniqueIdentifier\":\"f15f7130-c17e-4861-bf72-6cc804baa029\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Get in!\",\"typewriter\":true}],\"id\":\"coachman-drakkar\",\"actions\":[\"travel_sequence_{duration_seconds_10}_{world_world}_{dest_x_473}_{dest_y_67}_{dest_z_3}_{dest_yaw_-90}_{buffer_x_3250}_{buffer_y_10}_{buffer_z_3249}_{buffer_yaw_-90}\"]},{\"uuid\":\"33117091-2817-48b8-9b62-4d83f52ce563\",\"uniqueIdentifier\":\"c2e777f1-3542-44ee-aeff-4b1f88da6b34\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"void\",\"id\":\"coachman-drakkar\"}]},\"falsyFollowup\":{\"uuid\":\"40bcd1c0-4690-47a9-a6aa-c981bd730dd7\",\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"b23b2d38-5f26-42a0-be19-8f8092df0cb7\",\"uniqueIdentifier\":\"be5fbd3a-e455-4764-83e7-a250a16d38ad\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"no.money\",\"id\":\"coachman-drakkar\"}]}}],\"uuid\":\"334f0a23-a172-4496-97e7-a9e26dcc2fff\"}},{\"label\":\"Neverminnd\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"4283f5bb-798f-4fd1-9838-74d5f46b8186\",\"uniqueIdentifier\":\"287ba304-717a-4303-aa34-7f26641bce74\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"farewell\",\"id\":\"coachman-drakkar\"}],\"uuid\":\"8f673cec-91ba-4ef8-901e-77305df69b67\"}}],\"content\":[{\"line\":\"Are you ready?\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"}],\"uuid\":\"a0094387-beb3-4b16-b996-e1a08d4c0b2b\"}},{\"label\":\"Nevermind\",\"actions\":[],\"followup\":{\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"5b0489ee-8420-49b8-b4f3-67cde805be19\",\"uniqueIdentifier\":\"98e617a6-5a6e-4fe7-84c0-3386e01f9d5e\",\"type\":\"TeleportBranchStep\",\"content\":[],\"teleportToUniqueIdentifier\":\"farewell\",\"id\":\"coachman-drakkar\"}],\"uuid\":\"6a5fa23d-0751-48e8-be62-5da5a7f98457\"}}],\"content\":[{\"line\":\"So then, where shall I take you?\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"},{\"uuid\":\"db1b07ce-3c89-4467-a0ec-578217f72bab\",\"uniqueIdentifier\":\"farewell\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"See you, traveler!\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"},{\"uuid\":\"e4f2d588-b7ff-4c81-a0d7-b4efd2f99cce\",\"uniqueIdentifier\":\"void\",\"type\":\"ConditionStep\",\"conditions\":[\"never_true\"],\"id\":\"coachman-drakkar\",\"truthyFollowup\":{\"uuid\":\"b3b83fdc-2ed9-4db6-8b38-a09a2dd2b158\",\"id\":\"coachman-drakkar\",\"steps\":[{\"uuid\":\"0acef732-268a-46dd-acc5-0bfbb9a58b78\",\"uniqueIdentifier\":\"no.money\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"Sorry, no free rides, traveler!\",\"typewriter\":true},{\"line\":\"Come back when you get some coins.\",\"typewriter\":true}],\"id\":\"coachman-drakkar\"}]}}]}");
    public static final Supplier<DialogBuilder> tutorial_peasant_minerDialog = () -> DialogBuilder.deserialize("{\n" +
            "  \"id\": \"tutorial-island.peasant-miner\",\n" +
            "  \"steps\": [\n" +
            "    {\n" +
            "      \"uniqueIdentifier\": \"69cada31-b3ea-4ce4-b13f-eb5e1db1c7a5\",\n" +
            "      \"type\": \"ConditionStep\",\n" +
            "      \"conditions\": [\n" +
            "        \"quest_started_{id_tutorial}\"\n" +
            "      ],\n" +
            "      \"id\": \"tutorial-island.peasant-miner\",\n" +
            "      \"truthyFollowup\": {\n" +
            "        \"id\": \"tutorial-island.peasant-miner\",\n" +
            "        \"steps\": []\n" +
            "      },\n" +
            "      \"falsyFollowup\": {\n" +
            "        \"id\": \"tutorial-island.peasant-miner\",\n" +
            "        \"steps\": [\n" +
            "          {\n" +
            "            \"uniqueIdentifier\": \"bee53034-8f68-4487-8060-920c358de9ef\",\n" +
            "            \"type\": \"ConditionStep\",\n" +
            "            \"conditions\": [\n" +
            "              \"quest_finished_{id_tutorial}\"\n" +
            "            ],\n" +
            "            \"id\": \"tutorial-island.peasant-miner\",\n" +
            "            \"falsyFollowup\": {\n" +
            "              \"id\": \"tutorial-island.peasant-miner\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"uniqueIdentifier\": \"eb2663b2-773c-4c63-b9dd-5a59a0ce499d\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"W.. What?\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"Who the hell are you?\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"uniqueIdentifier\": \"7e22eef1-e04c-4a82-8eb5-889fe9d30885\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Oh, whatever. Go tell the §6tutor §fthat you're new around here.\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"type\": \"ConditionStep\",\n" +
            "                  \"uniqueIdentifier\": \"77e2bac5-8ed5-40c3-b183-111472a7f9fa\",\n" +
            "                  \"content\": [],\n" +
            "                  \"conditions\": [\n" +
            "                    \"inventory_contains_one_of_{item_id_1100}_{amount_1}\"\n" +
            "                  ],\n" +
            "                  \"falsyFollowup\": {\n" +
            "                    \"id\": \"tutorial-island.peasant-miner\",\n" +
            "                    \"steps\": [\n" +
            "                      {\n" +
            "                        \"type\": \"PassiveDialogStep\",\n" +
            "                        \"uniqueIdentifier\": \"934468c2-d529-4ee8-97e6-c32249909831\",\n" +
            "                        \"content\": [\n" +
            "                          {\n" +
            "                            \"line\": \"Wait\",\n" +
            "                            \"typewriter\": true\n" +
            "                          },\n" +
            "                          {\n" +
            "                            \"line\": \"Here, this will be useful to you\",\n" +
            "                            \"typewriter\": true\n" +
            "                          }\n" +
            "                        ],\n" +
            "                        \"actions\": [\n" +
            "                          \"give_item_{id_1100}_{amount_1}\"\n" +
            "                        ]\n" +
            "                      }\n" +
            "                    ]\n" +
            "                  }\n" +
            "                },\n" +
            "                {\n" +
            "                  \"type\": \"ConditionStep\",\n" +
            "                  \"uniqueIdentifier\": \"ff5f0cf0-4d65-4d8c-82c0-7443cf9c5155\",\n" +
            "                  \"content\": [],\n" +
            "                  \"conditions\": [\n" +
            "                    \"start_quest_if_requirements_met_{id_tutorial}\"\n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"uniqueIdentifier\": \"b12e1c64-2d84-457a-a249-2e2fabcb9aab\",\n" +
            "                  \"content\": [],\n" +
            "                  \"actions\": [\n" +
            "                    \"teleport_player_{world_world}_{x_2435}_{y_155}_{z_2485}\"\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            \"content\": []\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}");

    public static final Supplier<DialogBuilder> sheepShearerDialog = () -> DialogBuilder.deserialize("{\n" +
            "  \"id\": \"sheep-shearer-dialog\",\n" +
            "  \"steps\": [\n" +
            "    {\n" +
            "      \"type\": \"ConditionStep\",\n" +
            "      \"uniqueIdentifier\": \"8ad3449d-c1dc-458c-a7a6-51d547bf4053\",\n" +
            "      \"content\": [],\n" +
            "      \"conditions\": [\n" +
            "        \"quest_finished_{id_sheep-shearer}\"\n" +
            "      ],\n" +
            "      \"logicalOperator\": \"AND\",\n" +
            "      \"truthyFollowup\": {\n" +
            "        \"id\": \"sheep-shearer-dialog\",\n" +
            "        \"steps\": [\n" +
            "          {\n" +
            "            \"type\": \"PassiveDialogStep\",\n" +
            "            \"uniqueIdentifier\": \"ef6493ba-e9cc-41dc-b908-1acd7b0d2a93\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"line\": \"It’s a peaceful day, isn’t it?\",\n" +
            "                \"typewriter\": true\n" +
            "              },\n" +
            "              {\n" +
            "                \"line\": \"Thanks to you, at least the flock isn’t plotting an escape.\",\n" +
            "                \"typewriter\": true\n" +
            "              }\n" +
            "            ],\n" +
            "            \"actions\": []\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"falsyFollowup\": {\n" +
            "        \"id\": \"sheep-shearer-dialog\",\n" +
            "        \"steps\": [\n" +
            "          {\n" +
            "            \"type\": \"ConditionStep\",\n" +
            "            \"uniqueIdentifier\": \"653b429d-6014-41b4-99f4-effeacd523f1\",\n" +
            "            \"content\": [],\n" +
            "            \"conditions\": [\n" +
            "              \"quest_started_{id_sheep-shearer}\"\n" +
            "            ],\n" +
            "            \"logicalOperator\": \"AND\",\n" +
            "            \"truthyFollowup\": {\n" +
            "              \"id\": \"sheep-shearer-dialog\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"type\": \"ConditionStep\",\n" +
            "                  \"uniqueIdentifier\": \"e552481b-3487-462e-ba4d-296d01222b33\",\n" +
            "                  \"content\": [],\n" +
            "                  \"conditions\": [\n" +
            "                    \"quest_step_active_{stable_id_sheep-shearer.wool-collected}\"\n" +
            "                  ],\n" +
            "                  \"logicalOperator\": \"AND\",\n" +
            "                  \"truthyFollowup\": {\n" +
            "                    \"id\": \"sheep-shearer-dialog\",\n" +
            "                    \"steps\": [\n" +
            "                      {\n" +
            "                        \"type\": \"ConditionStep\",\n" +
            "                        \"uniqueIdentifier\": \"ec88f2a7-7118-45bc-94fd-b6a35d72a541\",\n" +
            "                        \"content\": [],\n" +
            "                        \"conditions\": [\n" +
            "                          \"inventory_contains_one_of_{item_id_2301}_{amount_20}\"\n" +
            "                        ],\n" +
            "                        \"logicalOperator\": \"AND\",\n" +
            "                        \"truthyFollowup\": {\n" +
            "                          \"id\": \"sheep-shearer-dialog\",\n" +
            "                          \"steps\": [\n" +
            "                            {\n" +
            "                              \"type\": \"PassiveDialogStep\",\n" +
            "                              \"uniqueIdentifier\": \"3b2b3de0-e9fe-42a7-b55c-3e09b7733b69\",\n" +
            "                              \"content\": [\n" +
            "                                {\n" +
            "                                  \"line\": \"Awesome, just what I needed!\",\n" +
            "                                  \"typewriter\": true\n" +
            "                                }\n" +
            "                              ],\n" +
            "                              \"actions\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                              \"type\": \"ConditionStep\",\n" +
            "                              \"uniqueIdentifier\": \"0f8d3309-95eb-4a36-a304-19ba07132c94\",\n" +
            "                              \"content\": [],\n" +
            "                              \"conditions\": [\n" +
            "                                \"remove_inventory_item_successful_{item_id_2301}_{amount_20}\"\n" +
            "                              ],\n" +
            "                              \"logicalOperator\": \"AND\",\n" +
            "                              \"truthyFollowup\": {\n" +
            "                                \"id\": \"sheep-shearer-dialog\",\n" +
            "                                \"steps\": [\n" +
            "                                  {\n" +
            "                                    \"type\": \"PassiveDialogStep\",\n" +
            "                                    \"uniqueIdentifier\": \"cde9630d-11f6-4a17-aa7b-38d50b609865\",\n" +
            "                                    \"content\": [\n" +
            "                                      {\n" +
            "                                        \"line\": \"\",\n" +
            "                                        \"typewriter\": true\n" +
            "                                      }\n" +
            "                                    ],\n" +
            "                                    \"actions\": [\n" +
            "                                      \"quest_step_finish_{stable_id_sheep-shearer.wool-collected}\"\n" +
            "                                    ]\n" +
            "                                  },\n" +
            "                                  {\n" +
            "                                    \"type\": \"PassiveDialogStep\",\n" +
            "                                    \"uniqueIdentifier\": \"b9d4f9cf-336e-4526-a89b-02fe6f2b959b\",\n" +
            "                                    \"content\": [\n" +
            "                                      {\n" +
            "                                        \"line\": \"Thanks, {PLAYER_NAME}!\",\n" +
            "                                        \"typewriter\": true\n" +
            "                                      },\n" +
            "                                      {\n" +
            "                                        \"line\": \"You did a really great job!\",\n" +
            "                                        \"typewriter\": true\n" +
            "                                      }\n" +
            "                                    ],\n" +
            "                                    \"actions\": []\n" +
            "                                  }\n" +
            "                                ]\n" +
            "                              }\n" +
            "                            }\n" +
            "                          ]\n" +
            "                        },\n" +
            "                        \"falsyFollowup\": {\n" +
            "                          \"id\": \"sheep-shearer-dialog\",\n" +
            "                          \"steps\": [\n" +
            "                            {\n" +
            "                              \"type\": \"PassiveDialogStep\",\n" +
            "                              \"uniqueIdentifier\": \"355dae53-b63e-4222-9a7f-e61aed6f2980\",\n" +
            "                              \"content\": [\n" +
            "                                {\n" +
            "                                  \"line\": \"Good, you collected the wool\",\n" +
            "                                  \"typewriter\": true\n" +
            "                                },\n" +
            "                                {\n" +
            "                                  \"line\": \"Now bring it to me.\",\n" +
            "                                  \"typewriter\": true\n" +
            "                                }\n" +
            "                              ],\n" +
            "                              \"actions\": []\n" +
            "                            }\n" +
            "                          ]\n" +
            "                        }\n" +
            "                      }\n" +
            "                    ]\n" +
            "                  },\n" +
            "                  \"falsyFollowup\": {\n" +
            "                    \"id\": \"sheep-shearer-dialog\",\n" +
            "                    \"steps\": [\n" +
            "                      {\n" +
            "                        \"type\": \"PassiveDialogStep\",\n" +
            "                        \"uniqueIdentifier\": \"e2a59167-d881-476d-b2cf-4766f56a19dc\",\n" +
            "                        \"content\": [\n" +
            "                          {\n" +
            "                            \"line\": \"You gonna work, or just slacking around?\",\n" +
            "                            \"typewriter\": true\n" +
            "                          },\n" +
            "                          {\n" +
            "                            \"line\": \"I need 20 balls of wool.\",\n" +
            "                            \"typewriter\": true\n" +
            "                          }\n" +
            "                        ],\n" +
            "                        \"actions\": []\n" +
            "                      }\n" +
            "                    ]\n" +
            "                  }\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            \"falsyFollowup\": {\n" +
            "              \"id\": \"sheep-shearer-dialog\",\n" +
            "              \"steps\": [\n" +
            "                {\n" +
            "                  \"type\": \"PassiveDialogStep\",\n" +
            "                  \"uniqueIdentifier\": \"0a6421c4-d53c-48a0-8632-5c27db9c2c42\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"Oi! What are you doing sneaking around my land?\",\n" +
            "                      \"typewriter\": true\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"line\": \"You're not the one letting my sheep out?\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"actions\": []\n" +
            "                },\n" +
            "                {\n" +
            "                  \"type\": \"InteractiveDialogStep\",\n" +
            "                  \"uniqueIdentifier\": \"86aacd2f-de6b-43cb-add0-4465139101e0\",\n" +
            "                  \"content\": [\n" +
            "                    {\n" +
            "                      \"line\": \"You looking for work or just snooping?\",\n" +
            "                      \"typewriter\": true\n" +
            "                    }\n" +
            "                  ],\n" +
            "                  \"options\": [\n" +
            "                    {\n" +
            "                      \"label\": \"Maybe… what if I was?\",\n" +
            "                      \"actions\": [],\n" +
            "                      \"followup\": {\n" +
            "                        \"id\": \"sheep-shearer-dialog\",\n" +
            "                        \"steps\": [\n" +
            "                          {\n" +
            "                            \"type\": \"PassiveDialogStep\",\n" +
            "                            \"uniqueIdentifier\": \"81e5a4f1-9355-4e1e-945d-682ddf1502bb\",\n" +
            "                            \"content\": [\n" +
            "                              {\n" +
            "                                \"line\": \"Don’t be clever with me.\",\n" +
            "                                \"typewriter\": true\n" +
            "                              }\n" +
            "                            ],\n" +
            "                            \"actions\": []\n" +
            "                          }\n" +
            "                        ]\n" +
            "                      }\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"label\": \"Sorry, won’t be long\",\n" +
            "                      \"actions\": [],\n" +
            "                      \"followup\": {\n" +
            "                        \"id\": \"sheep-shearer-dialog\",\n" +
            "                        \"steps\": [\n" +
            "                          {\n" +
            "                            \"type\": \"PassiveDialogStep\",\n" +
            "                            \"uniqueIdentifier\": \"ba7515a8-bbfb-49da-bc07-499a3eaf2a1b\",\n" +
            "                            \"content\": [\n" +
            "                              {\n" +
            "                                \"line\": \"Fine - don’t let my sheep out!\",\n" +
            "                                \"typewriter\": true\n" +
            "                              }\n" +
            "                            ],\n" +
            "                            \"actions\": []\n" +
            "                          }\n" +
            "                        ]\n" +
            "                      }\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"label\": \"I'm looking for work\",\n" +
            "                      \"actions\": [],\n" +
            "                      \"followup\": {\n" +
            "                        \"id\": \"sheep-shearer-dialog\",\n" +
            "                        \"steps\": [\n" +
            "                          {\n" +
            "                            \"type\": \"PassiveDialogStep\",\n" +
            "                            \"uniqueIdentifier\": \"24f5ecdf-699e-416c-b0e0-316a3abf6d74\",\n" +
            "                            \"content\": [\n" +
            "                              {\n" +
            "                                \"line\": \"Good - I need twenty balls of wool from my sheep.\",\n" +
            "                                \"typewriter\": true\n" +
            "                              },\n" +
            "                              {\n" +
            "                                \"line\": \"Shear them, then spin the wool at the spinning wheel\",\n" +
            "                                \"typewriter\": true\n" +
            "                              },\n" +
            "                              {\n" +
            "                                \"line\": \"I'm pretty sure you can find spinning wheel at Drakkar.\",\n" +
            "                                \"typewriter\": true\n" +
            "                              }\n" +
            "                            ],\n" +
            "                            \"actions\": []\n" +
            "                          },\n" +
            "                          {\n" +
            "                            \"type\": \"ConditionStep\",\n" +
            "                            \"uniqueIdentifier\": \"486c12c0-e773-4e03-a18b-dea50c7674f3\",\n" +
            "                            \"content\": [],\n" +
            "                            \"conditions\": [\n" +
            "                              \"start_quest_if_requirements_met_{id_sheep-shearer}\"\n" +
            "                            ],\n" +
            "                            \"logicalOperator\": \"AND\",\n" +
            "                            \"truthyFollowup\": {\n" +
            "                              \"id\": \"sheep-shearer-dialog\",\n" +
            "                              \"steps\": [\n" +
            "                                {\n" +
            "                                  \"type\": \"PassiveDialogStep\",\n" +
            "                                  \"uniqueIdentifier\": \"674f428d-4fd1-48fa-811c-224f1398715f\",\n" +
            "                                  \"content\": [\n" +
            "                                    {\n" +
            "                                      \"line\": \"\",\n" +
            "                                      \"typewriter\": true\n" +
            "                                    }\n" +
            "                                  ],\n" +
            "                                  \"actions\": [\n" +
            "                                    \"give_item_{id_3}_{amount_1}\"\n" +
            "                                  ]\n" +
            "                                },\n" +
            "                                {\n" +
            "                                  \"type\": \"PassiveDialogStep\",\n" +
            "                                  \"uniqueIdentifier\": \"bf8e62c8-ea8d-4eed-aa0b-9a7daa09bea5\",\n" +
            "                                  \"content\": [\n" +
            "                                    {\n" +
            "                                      \"line\": \"Here, take these.\",\n" +
            "                                      \"typewriter\": true\n" +
            "                                    },\n" +
            "                                    {\n" +
            "                                      \"line\": \"And remember. 20 balls of wool!\",\n" +
            "                                      \"typewriter\": true\n" +
            "                                    }\n" +
            "                                  ],\n" +
            "                                  \"actions\": []\n" +
            "                                }\n" +
            "                              ]\n" +
            "                            }\n" +
            "                          }\n" +
            "                        ]\n" +
            "                      }\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}");


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
