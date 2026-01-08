package org.zuma.examplePlugin.domain.block.constants;

import org.bukkit.Material;

/*Рослисть (Dewleaf) – Маленькие, круглые сине-зелёные листья. Похоже на небольшой клеверный кустик. Мягкие края, слегка блестят, как после утренней росы.

Солнечный корень (Sunroot) – Толстый золотисто-оранжевый корень, торчащий из земли. Сверху маленькие зелёные листья, напоминает морковь, но толще и массивнее.

Лунная тень (Moonshade) – Бледно-серебристые листья, длинные и тонкие, почти как веточки ивы. Слегка прозрачные.

Багровый чертополох (Crimson Thistle) – Красный колючий цветок с зубчатыми зелёными листьями. Лёгкая колючая аура, маленькие шипы на стебле.

Морозная шапка (Frostcap) – Синий гриб с широкой плоской шляпкой. На поверхности узоры, похожие на мороз, короткая белая ножка.

Светящийся папоротник (Glowfern) – Тонкие, изящные папоротниковые листья с слабым голубым свечением по краям. Листья слегка завиваются вверх.

Грозовая лиана (Stormvine) – Тёмно-зелёная скрученная лиана, закручивается сама вокруг себя. На листьях мелкие узоры, похожие на молнии.

Кровавая орхидея (Bloodorchid) – Тёмно-красные восковые лепестки в форме открытой звезды. Листья тёмно-зелёные, толстые, с небольшими зубчатыми краями.

Дыхание дракона (Dragon’s Breath) – Оранжево-красный колючий цветок, лепестки словно языки пламени. Толстый зелёный стебель с мелкими заострёнными листьями.

Эфирный цветок (Ethereal Bloom) – Многоцветные переливающиеся лепестки (пастельная радуга), мягкие округлые листья, почти прозрачные, с лёгким свечением.*/

public enum RetexturedStem {
    DEWLEAF(0, Material.WHEAT),
    SUNROOT(1, Material.WHEAT),
    MOONSHADE(2, Material.WHEAT),
    CRIMSON_THISTLE(3, Material.WHEAT),
    FROSTCAP(4, Material.WHEAT),
    GLOWFERN(0, Material.POTATOES),
    STORMVINE(2, Material.POTATOES),
    BLOODORCHID(4, Material.POTATOES),
    DRAGONS_BREATH(0, Material.CARROT),
    ETHEREAL_BLOOM(2, Material.CARROT);


    private final int age;
    private final Material stemMaterial;

    RetexturedStem(int age, Material stemMaterial) {
        this.age = age;
        this.stemMaterial = stemMaterial;
    }

    public int getAge() {
        return age;
    }

    public Material getStemMaterial() {
        return stemMaterial;
    }
}
