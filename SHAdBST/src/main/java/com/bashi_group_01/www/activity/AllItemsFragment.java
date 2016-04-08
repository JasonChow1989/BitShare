package com.bashi_group_01.www.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bashi_group_01.www.childactivity.CarnewsActivity;
import com.bashi_group_01.www.childactivity.ChenumselectActivity;
import com.bashi_group_01.www.childactivity.ChepanselectActivity;
import com.bashi_group_01.www.childactivity.ComplnewsActivity;
import com.bashi_group_01.www.childactivity.EnmeryActivity;
import com.bashi_group_01.www.childactivity.EventchackActivity;
import com.bashi_group_01.www.childactivity.FeedonlogActivity;
import com.bashi_group_01.www.childactivity.FileMakeActivity;
import com.bashi_group_01.www.childactivity.HelpintoActivity;
import com.bashi_group_01.www.childactivity.HostroyActivity;
import com.bashi_group_01.www.childactivity.HoucheselectActivity;
import com.bashi_group_01.www.childactivity.LineselectActivity;
import com.bashi_group_01.www.childactivity.MedianewsActivity;
import com.bashi_group_01.www.childactivity.MeetnotiyActivity;
import com.bashi_group_01.www.childactivity.OrderselectActivity;
import com.bashi_group_01.www.childactivity.PinpanselectActivity;
import com.bashi_group_01.www.childactivity.RoadNameActivity;
import com.bashi_group_01.www.childactivity.ShangingselectActivity;
import com.bashi_group_01.www.childactivity.StatpanselectActivity;
import com.bashi_group_01.www.childactivity.XianluselectActivity;
import com.bashi_group_01.www.childactivity.ZhangpanselectActivity;
/**
 * @author zzy
 *	更多。。fragment
 *
 */
public class AllItemsFragment extends Fragment implements OnClickListener {

	private String[] items = { "交办事务", "现场办公" };
	private RadioButton rb_items_lumingselect, rb_items_lineselect,
			rb_items_statpanselect, rb_items_houcheselect,
			rb_items_zhangpanselect, rb_items_shangingselect,
			rb_items_eventstart, rb_items_eventchack, rb_items_jinjievent,
			rb_items_meetnotiy, rb_items_filejianfa, rb_items_xianluselect,
			rb_items_chepanselect, rb_items_pinpanselect,
			rb_items_chenumselect, rb_items_orderselect, rb_items_stit,
			rb_items_carmid, rb_items_elcMah, rb_items_fifty,
			rb_items_feedselect, rb_items_feedonlog, rb_items_hostroy,
			rb_items_carremaind, rb_items_changeselect, rb_items_complnews,
			rb_items_cernews, rb_items_medianews, rb_items_helpinto,
			rb_item_taskPross, rb_item_task;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_allitems, null);
		initViews(view);
		initEvent();
		return view;
	}

	private void initViews(View v) {
		// this.rb_title = (ImageView) v.findViewById(R.id.img_title);
		this.rb_items_lumingselect = (RadioButton) v
				.findViewById(R.id.rb_items_lumingselect);
		this.rb_items_lineselect = (RadioButton) v
				.findViewById(R.id.rb_items_lineselect);
		this.rb_items_statpanselect = (RadioButton) v
				.findViewById(R.id.rb_items_statpanselect);
		this.rb_items_houcheselect = (RadioButton) v
				.findViewById(R.id.rb_items_houcheselect);
		this.rb_items_zhangpanselect = (RadioButton) v
				.findViewById(R.id.rb_items_zhangpanselect);
		this.rb_items_shangingselect = (RadioButton) v
				.findViewById(R.id.rb_items_shangingselect);
		this.rb_items_eventstart = (RadioButton) v
				.findViewById(R.id.rb_items_eventstart);
		this.rb_items_eventchack = (RadioButton) v
				.findViewById(R.id.rb_items_eventchack);
		this.rb_items_jinjievent = (RadioButton) v
				.findViewById(R.id.rb_items_jinjievent);
		this.rb_items_meetnotiy = (RadioButton) v
				.findViewById(R.id.rb_items_meetnotiy);
		this.rb_items_filejianfa = (RadioButton) v
				.findViewById(R.id.rb_items_filejianfa);
		this.rb_items_xianluselect = (RadioButton) v
				.findViewById(R.id.rb_items_xianluselect);
		this.rb_items_chepanselect = (RadioButton) v
				.findViewById(R.id.rb_items_chepanselect);
		this.rb_items_pinpanselect = (RadioButton) v
				.findViewById(R.id.rb_items_pinpanselect);
		this.rb_items_chenumselect = (RadioButton) v
				.findViewById(R.id.rb_items_chenumselect);
		this.rb_items_orderselect = (RadioButton) v
				.findViewById(R.id.rb_items_orderselect);
		this.rb_items_stit = (RadioButton) v.findViewById(R.id.rb_items_stit);
		this.rb_items_carmid = (RadioButton) v
				.findViewById(R.id.rb_items_carmid);
		this.rb_items_elcMah = (RadioButton) v
				.findViewById(R.id.rb_items_elcMah);
		this.rb_items_fifty = (RadioButton) v.findViewById(R.id.rb_items_fifty);
		this.rb_items_feedselect = (RadioButton) v
				.findViewById(R.id.rb_items_feedselect);
		this.rb_items_feedonlog = (RadioButton) v
				.findViewById(R.id.rb_items_feedonlog);
		this.rb_items_hostroy = (RadioButton) v
				.findViewById(R.id.rb_items_hostroy);
		this.rb_items_carremaind = (RadioButton) v
				.findViewById(R.id.rb_items_carremaind);
		this.rb_items_changeselect = (RadioButton) v
				.findViewById(R.id.rb_items_changeselect);
		this.rb_items_complnews = (RadioButton) v
				.findViewById(R.id.rb_items_complnews);
		this.rb_items_cernews = (RadioButton) v
				.findViewById(R.id.rb_items_cernews);
		this.rb_items_medianews = (RadioButton) v
				.findViewById(R.id.rb_items_medianews);
		this.rb_items_helpinto = (RadioButton) v
				.findViewById(R.id.rb_items_helpinto);
		this.rb_item_taskPross = (RadioButton) v
				.findViewById(R.id.rb_items_renwumake);
		this.rb_item_task = (RadioButton) v
				.findViewById(R.id.rb_items_message_renwu);
	}

	private void initEvent() {
		// this.rb_title.setOnClickListener(this);
		this.rb_items_lumingselect.setOnClickListener(this);
		this.rb_items_lineselect.setOnClickListener(this);
		this.rb_items_statpanselect.setOnClickListener(this);
		this.rb_items_houcheselect.setOnClickListener(this);
		this.rb_items_zhangpanselect.setOnClickListener(this);
		this.rb_items_shangingselect.setOnClickListener(this);
	
		//事务发起;
		this.rb_items_eventstart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(getActivity())
						.setItems(items, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent();
								switch (which) {
								case 0:
									intent.setClass(getActivity(),
											EventGetAndDoActivity.class);
									break;
								case 1:
									intent.setClass(getActivity(),
											EventLiveWorkingActivity.class);
									break;
								}
								dialog.cancel();
								startActivity(intent);
							}
						}).create().show();

			}
		});
		this.rb_items_eventchack.setOnClickListener(this);
		this.rb_items_jinjievent.setOnClickListener(this);
		this.rb_items_meetnotiy.setOnClickListener(this);
		this.rb_items_filejianfa.setOnClickListener(this);
		this.rb_items_xianluselect.setOnClickListener(this);
		this.rb_items_chepanselect.setOnClickListener(this);
		this.rb_items_pinpanselect.setOnClickListener(this);
		this.rb_items_chenumselect.setOnClickListener(this);
		this.rb_items_orderselect.setOnClickListener(this);
		this.rb_items_stit.setOnClickListener(this);
		this.rb_items_carmid.setOnClickListener(this);
		this.rb_items_elcMah.setOnClickListener(this);
		this.rb_items_fifty.setOnClickListener(this);
		this.rb_items_feedselect.setOnClickListener(this);
		this.rb_items_feedonlog.setOnClickListener(this);
		this.rb_items_hostroy.setOnClickListener(this);
		this.rb_items_carremaind.setOnClickListener(this);
		this.rb_items_changeselect.setOnClickListener(this);
		this.rb_items_complnews.setOnClickListener(this);
		this.rb_items_cernews.setOnClickListener(this);
		this.rb_items_medianews.setOnClickListener(this);
		this.rb_items_helpinto.setOnClickListener(this);
		this.rb_item_task.setOnClickListener(this);
		this.rb_item_taskPross.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		Intent intent = new Intent();
		switch (id) {
		case R.id.img_title:
			getActivity().finish();
			break;
		case R.id.rb_items_lumingselect:
			intent.setClass(getActivity(), RoadNameActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_statpanselect:
			intent.setClass(getActivity(), StatpanselectActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_lineselect:
			intent.setClass(getActivity(), LineselectActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_houcheselect:
			intent.setClass(getActivity(), HoucheselectActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_zhangpanselect:
			intent.setClass(getActivity(), ZhangpanselectActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_shangingselect:
			intent.setClass(getActivity(), ShangingselectActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_eventchack:
			intent.setClass(getActivity(), EventchackActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_jinjievent:
			intent.setClass(getActivity(), EnmeryActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_meetnotiy:
			intent.setClass(getActivity(), MeetnotiyActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_filejianfa:
			intent.setClass(getActivity(), FileMakeActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_xianluselect:
			intent.setClass(getActivity(), XianluselectActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_chepanselect:
			intent.setClass(getActivity(), ChepanselectActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_pinpanselect:
			intent.setClass(getActivity(), PinpanselectActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_chenumselect:
			intent.setClass(getActivity(), ChenumselectActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_orderselect:
			intent.setClass(getActivity(), OrderselectActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_stit:
			String url_stit = "http://116.236.170.106:9001/MobileAdWeb/RoadCompanySummary.aspx";
			String titlename = "报表查询-候车亭查询";
			intent.putExtra("url", url_stit);
			intent.putExtra("titlename", titlename);
			intent.setClass(getActivity(), FirstWebViewsHengActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_carmid:
			String url = "http://116.236.170.106:9001/MobileAdWeb/PtCompany.aspx";
			String titleName = "报表查询-车身媒体";
			intent.putExtra("url", url);
			intent.putExtra("titlename", titleName);
			intent.setClass(getActivity(), FirstWebViewsHengActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_elcMah:
			String url1 = "http://116.236.170.106:9001/MobileAdWeb/ElectronicSum.aspx";
			String titleName1 = "报表查询-电子设备";
			intent.putExtra("url", url1);
			intent.putExtra("titlename", titleName1);
			intent.setClass(getActivity(), FirstWebViewsActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_fifty:
			String url_movetv = "http://116.236.170.106:9001/MobileAdWeb/VehicleVideoList.aspx";
			String titlename_tv = "报表查询-移动设备";
			intent.putExtra("url", url_movetv);
			intent.putExtra("titlename", titlename_tv);
			intent.setClass(getActivity(), FirstWebViewsHengActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_feedselect:
			String url_feedselect = "http://116.236.170.106:9001/MaintenancePolesStation.aspx";
			String titlename_feed = "站点养护-养护查询";
			intent.putExtra("url", url_feedselect);
			intent.putExtra("titlename", titlename_feed);
			intent.setClass(getActivity(), FirstWebViewsHengActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_feedonlog:
			intent.setClass(getActivity(), FeedonlogActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_hostroy:
			intent.setClass(getActivity(), HostroyActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_carremaind:
			String url_remaind = "http://61.129.57.81:8181/BusEstimate.aspx";
			String titlename_re = "绿色出行-公交预报";
			intent.putExtra("url", url_remaind);
			intent.putExtra("titlename", titlename_re);
			intent.setClass(getActivity(), FirstWebViewsActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_changeselect:
			String url_change = "http://map.baidu.com/mobile/webapp/index/index";
			String titlename_ch = "绿色出行-换乘查询";
			intent.putExtra("url", url_change);
			intent.putExtra("titlename", titlename_ch);
			intent.setClass(getActivity(), FirstWebViewsActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_complnews:
			intent.setClass(getActivity(), ComplnewsActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_cernews:
			intent.setClass(getActivity(), CarnewsActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_medianews:
			intent.setClass(getActivity(), MedianewsActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_helpinto:
			intent.setClass(getActivity(), ShareAppActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_renwumake:
			intent.setClass(getActivity(), MsgScreenTaskProgress.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		case R.id.rb_items_message_renwu:
			intent.setClass(getActivity(), MsgScreenTask.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin,
					R.anim.others_getout);
			break;
		}
	}
}
