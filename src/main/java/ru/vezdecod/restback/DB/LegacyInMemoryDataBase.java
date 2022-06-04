package ru.vezdecod.restback.DB;

public class LegacyInMemoryDataBase {
    private static final LegacyInMemoryDataBase instance = new LegacyInMemoryDataBase();

    private LegacyInMemoryDataBase() {

    }

    public static LegacyInMemoryDataBase getInstance() {
        return instance
    }
}
