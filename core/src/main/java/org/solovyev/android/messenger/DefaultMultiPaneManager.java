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
import android.app.Application;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;
import org.solovyev.android.Views;
import org.solovyev.android.messenger.core.R;
import org.solovyev.common.text.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Singleton
public class DefaultMultiPaneManager implements MultiPaneManager {

	@Nonnull
	private final Application context;

	@Inject
	public DefaultMultiPaneManager(@Nonnull Application context) {
		this.context = context;
	}

	@Override
	public boolean isDualPane(@Nonnull Activity activity) {
		if (activity.findViewById(R.id.content_second_pane) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isTriplePane(@Nonnull Activity activity) {
		if (activity.findViewById(R.id.content_third_pane) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isFirstPane(@Nullable View parent) {
		return parent != null && parent.getId() == R.id.content_first_pane;
	}

	@Override
	public boolean isSecondPane(@Nullable View parent) {
		return parent != null && parent.getId() == R.id.content_second_pane;
	}

	@Override
	public boolean isThirdPane(@Nullable View parent) {
		return parent != null && parent.getId() == R.id.content_third_pane;
	}

	@Override
	public void onCreatePane(@Nonnull Activity activity, @Nullable View paneParent, @Nonnull View pane) {
		if (this.isDualPane(activity)) {
			if (this.isFirstPane(paneParent)) {
				pane.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.mpp_border_right));
				// border may add padding => set to zeros
				pane.setPadding(0, 0, 0, 0);
			} else if (this.isSecondPane(paneParent)) {
				pane.setBackgroundColor(context.getResources().getColor(R.color.mpp_bg));
			} else if (this.isTriplePane(activity) && this.isThirdPane(paneParent)) {
				if (Views.getScreenOrientation(activity) == Configuration.ORIENTATION_LANDSCAPE) {
					pane.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.mpp_border_left));
				} else {
					pane.setBackgroundColor(context.getResources().getColor(R.color.mpp_bg));
				}
			}
		} else {
			pane.setBackgroundColor(context.getResources().getColor(R.color.mpp_bg));
		}
	}

	@Override
	public void onPaneCreated(@Nonnull Activity activity, @Nonnull View pane) {
		onPaneCreated(activity, pane, false);
	}
	@Override
	public void onPaneCreated(@Nonnull Activity activity, @Nonnull View pane, boolean forceShowTitle) {
		final TextView fragmentTitleTextView = (TextView) pane.findViewById(R.id.mpp_fragment_title);
		if (fragmentTitleTextView != null) {
			if (!forceShowTitle) {
				if (this.isDualPane(activity)) {
					prepareTitle(fragmentTitleTextView);
				} else {
					fragmentTitleTextView.setVisibility(View.GONE);
				}
			} else {
				prepareTitle(fragmentTitleTextView);
			}
		}
	}

	private void prepareTitle(@Nonnull TextView titleTextView) {
		final CharSequence fragmentTitle = titleTextView.getText();
		if (Strings.isEmpty(fragmentTitle)) {
			titleTextView.setVisibility(View.GONE);
		} else {
			titleTextView.setText(String.valueOf(fragmentTitle).toUpperCase());
			titleTextView.setVisibility(View.VISIBLE);
		}
	}
}
