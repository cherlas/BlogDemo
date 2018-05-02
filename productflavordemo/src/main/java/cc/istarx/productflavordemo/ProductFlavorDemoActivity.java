package cc.istarx.productflavordemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductFlavorDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_flavor_demo);

        LinearLayout beiJingLayout = findViewById(R.id.layout_beiJing);
        LinearLayout shanDongLayout = findViewById(R.id.layout_shanDong);
        LinearLayout shanXiLayout = findViewById(R.id.layout_shanXi);
        TextView desc;

        // beiJingChargeDemo
        if (BjcdFeature.LAYOUT_DISPLAY) {
            shanDongLayout.setVisibility(View.GONE);
            shanXiLayout.setVisibility(View.GONE);
            desc = beiJingLayout.findViewById(R.id.beiJingFlavorDesc);
            desc.setText(R.string.beiJingFlavorDesc);
        }

        // shanDongFreeDebug
        if ("shanDongFree".equals(BuildConfig.FLAVOR) && "debug".equals(BuildConfig.BUILD_TYPE)) {
            beiJingLayout.setVisibility(View.GONE);
            shanXiLayout.setVisibility(View.GONE);
            desc = shanDongLayout.findViewById(R.id.shanDongFlavorDesc);
            desc.setText(R.string.shanDongFlavorDesc);
        }

        // shanXiChargeDemo
        if (ProductFlavorDemo.isShowLayout()) {
            beiJingLayout.setVisibility(View.GONE);
            shanDongLayout.setVisibility(View.GONE);
            desc = shanXiLayout.findViewById(R.id.shanXiFlavorDesc);
            desc.setText(R.string.shanXiFlavorDesc);
        }
    }
}
