FixedOrderDialog
================

As far as you probably know there is difference in order of positive and negative buttons for AlertDialog on pre-ICS and post-ICS devices:
  - on devices prior to ICS, the button order (left to right) was POSITIVE - NEUTRAL - NEGATIVE.
  - on newer devices using ICS, the button order (left to right) is now NEGATIVE - NEUTRAL - POSITIVE.

However the order is not guaranteed to be as listed above for post-ICS devices. Some manufactures ignore button order reversing.

If you want to see one order on all devices nomatter of Android firmware version, you can use [FixedOrderDialogFragment](/src/shared/dialog/fixedorderdialog/FixedOrderDialogFragment.java).

Please note that owner Activity should implement FixedOrderDialogFragment.FixedOrderDialogListener to be able restore dialog state on Activity re-creation.

Examples
=============

Here is an example of dialog creation and showing:
```java
new FixedOrderDialogFragment.Builder()
    .setLeftButtonText(R.string.left)
    .setCenterButtonText(R.string.center)
    .setRightButtonText(R.string.right)
    .setDefaultButton(FixedOrderDialogFragment.BUTTON_RIGHT)
    .setMessage(R.string.message)
    .setTitle(R.string.app_name)
    .create()
.show(getSupportFragmentManager(), "DEMO");
```

-------------------------------------------------------------------------------

Developed By
============

* Steven Rudenko - <steven.rudenko@gmail.com>

License
=======

    Copyright 2013 Steven Rudenko

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
