package org.solovyev.android.messenger.chats;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.solovyev.android.messenger.entities.Entity;
import org.solovyev.android.messenger.messages.Message;

/**
 * User: serso
 * Date: 6/6/12
 * Time: 4:18 PM
 */
public enum ChatEventType {
	added,
	changed,

	participant_added,
	participant_removed,

	message_added,
	message_added_batch {
		@Override
		protected void checkData(@Nullable Object data) {
			assert data instanceof List;
		}
	},

	// data == changed message for chat
	message_state_changed,

	// data == changed message for chat
	message_changed {
		@Override
		protected void checkData(@Nullable Object data) {
			assert data instanceof Message;
		}
	},

	// data == new last message for chat
	last_message_changed {
		@Override
		protected void checkData(@Nullable Object data) {
			assert data instanceof Message;
		}
	},

	// data == user which start typing in chat
	user_starts_typing {
		@Override
		protected void checkData(@Nullable Object data) {
			assert data instanceof Entity;
		}
	},

	// data == user which stop typing in chat
	user_stops_typing {
		@Override
		protected void checkData(@Nullable Object data) {
			assert data instanceof Entity;
		}
	},

	// data == number of unread messages
	unread_message_count_changed {
		@Override
		protected void checkData(@Nullable Object data) {
			assert data instanceof Integer;
		}
	},

	message_read {
		@Override
		protected void checkData(@Nullable Object data) {
			assert data instanceof Message;
		}
	};

	public final boolean isEvent(@Nonnull ChatEventType chatEventType, @Nonnull Chat eventChat, @Nonnull Chat chat) {
		return this == chatEventType && eventChat.equals(chat);
	}

	@Nonnull
	public final ChatEvent newEvent(@Nonnull Chat chat) {
		return newEvent(chat, null);
	}

	@Nonnull
	public final ChatEvent newEvent(@Nonnull Chat chat, @Nullable Object data) {
		checkData(data);
		return new ChatEvent(chat, this, data);
	}

	protected void checkData(@Nullable Object data) {
		assert data == null;
	}

}
