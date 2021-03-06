/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.solovyev.android.messenger;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface MultiPaneManager {

	boolean isDualPane(@Nonnull Activity activity);

	boolean isTriplePane(@Nonnull Activity activity);

	boolean isFirstPane(@Nullable View parent);

	boolean isSecondPane(@Nullable View parent);

	boolean isThirdPane(@Nullable View parent);

	void onCreatePane(@Nonnull Activity activity, @Nullable View paneParent, @Nonnull View pane);

	void onPaneCreated(@Nonnull Activity activity, @Nonnull View pane);
	void onPaneCreated(@Nonnull Activity activity, @Nonnull View pane, boolean forceShowTitle);
}
