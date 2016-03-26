package com.sekwah.sekcphysics.settings;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModSettings {


    public static Configuration config = null;

    public static int usageReportMod = 0;

    public static void changeSetting() {
    }

    /**
     * public static void changeSettingBoolean(EnumNarutoOptions setting, boolean bool) {
     * if(setting == EnumNarutoOptions.FIRSTPERSON){
     * experimentalFirstPerson = bool;
     * config.get(Configuration.CATEGORY_GENERAL, "experimentalFirstPersonEnabled", false).set(bool);
     * }
     * }
     */

    public static void saveConfig() {
        config.save();
    }


    public static void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();


        Property configUsageReportMod = config.get(Configuration.CATEGORY_GENERAL, "usageReportMod", 0);
        usageReportMod = configUsageReportMod.getInt(0);
        configUsageReportMod.setComment("This sets the usage report mode 0 = Enabled, 1 = No data sent but says its " +
                "online, 2 = Disabled (please leave this on 1 at least just for me :3 the data is anonymous and it " +
                "lets me see how many people are playing. If you dont like data being sent about your pc such as " +
                "operaing system and cores then please set it to 1 so we have a usage count)");

        //int randomBlockID = config.getBlock("RandomBlock", 200).getInt();

        //int randomItemID = config.getItem("RandomItem", 20000).getInt();

        // Since this flag is a boolean, we can read it into the variable directly from the config.
        //someConfigFlag = config.get(Configuration.CATEGORY_GENERAL, "SomeConfigFlag", false).getBoolean(false);

        //Notice there is nothing that gets the value of this property so the expression results in a Property object.
        //Property someProperty = config.get(Configuration.CATEGORY_GENERAL, "SomeConfigString", "nothing");

        // Here we add a comment to our new property.
        //someProperty.comment = "This value can be read as a string!";

        //String someConfigString = someProperty.value;
        // this could also be:
        // int someInt = someProperty.getInt();
        // boolean someBoolean = someProperty.getBoolean(true);


        config.save();

    }

}
