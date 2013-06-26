/*******************************************************************************
 * Copyright 2013 Steven Rudenko
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package shared.dialog.fixedorderdialog;

import shared.dialog.fixedorderdialog.R;
import shared.dialog.fixedorderdialog.FixedOrderDialogFragment.FixedOrderDialogListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements FixedOrderDialogListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void onSourceCodeClick(View view) {
    final Intent i = new Intent(Intent.ACTION_VIEW);
    i.setData(Uri.parse(getString(R.string.sources_link)));
    startActivity(i);
  }

  public void onDemoClick(View view) {
    new FixedOrderDialogFragment.Builder()
    .setLeftButtonText(R.string.left)
    .setCenterButtonText(R.string.center)
    .setRightButtonText(R.string.right)
    .setDefaultButton(FixedOrderDialogFragment.BUTTON_RIGHT)
    .setMessage(R.string.message)
    .setTitle(R.string.app_name)
    .create().show(getSupportFragmentManager(), "DEMO");
  }

  @Override
  public void onDialogButtonClick(FixedOrderDialogFragment f, int button) {
    final int message;
    switch (button) {
    case FixedOrderDialogFragment.BUTTON_LEFT:
      message = R.string.left_button_clicked;
      break;
    case FixedOrderDialogFragment.BUTTON_CENTER:
      message = R.string.center_button_clicked;
      break;
    case FixedOrderDialogFragment.BUTTON_RIGHT:
      message = R.string.right_button_clicked;
      break;
    default:
      return;
    }

    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onDialogCanceled(FixedOrderDialogFragment f) {
    // we don't care about this
  }
}
