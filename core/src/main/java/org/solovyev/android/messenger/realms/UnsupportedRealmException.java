package org.solovyev.android.messenger.realms;

import javax.annotation.Nonnull;

public class UnsupportedRealmException extends RealmException {

	public UnsupportedRealmException(@Nonnull String realmId) {
		super(realmId);
	}

	public UnsupportedRealmException(@Nonnull String realmId, @Nonnull Throwable throwable) {
		super(realmId, throwable);
	}
}