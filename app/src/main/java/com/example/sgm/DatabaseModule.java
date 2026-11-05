package com.example.sgm;
import android.content.Context;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;
@Module
@InstallIn(SingletonComponent.class)
public final class DatabaseModule {
    private DatabaseModule() {}
    @Provides @Singleton
    public static SGMDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, SGMDatabase.class, "sgm_local.db").build();
    }
    @Provides public static ClienteDao provideClienteDao(SGMDatabase db) { return db.clienteDao(); }
    @Provides public static VeiculoDao provideVeiculoDao(SGMDatabase db) { return db.veiculoDao(); }
}
