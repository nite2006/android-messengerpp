package org.solovyev.android.messenger.users;

import org.solovyev.android.messenger.AbstractAsyncLoader;
import org.solovyev.android.messenger.MessengerListItemAdapter;
import org.solovyev.android.view.ListViewAwareOnRefreshListener;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 6/2/12
 * Time: 5:14 PM
 */
public class OnlineContactsFragment extends AbstractContactsFragment {

	@Nonnull
	@Override
	protected AbstractAsyncLoader<UiContact, ContactListItem> createAsyncLoader(@Nonnull MessengerListItemAdapter<ContactListItem> adapter, @Nonnull Runnable onPostExecute) {
		return new OnlineContactsAsyncLoader(getActivity(), adapter, onPostExecute, getAccountService());
	}

	@Nonnull
	protected AbstractContactsAdapter createAdapter() {
		return new OnlineContactsAdapter(getActivity(), getAccountService());
	}

	@Override
	protected ListViewAwareOnRefreshListener getTopPullRefreshListener() {
		return new ContactsSyncRefreshListener();
	}
}