package vn.edu.hust.activityexamples

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class DanhBa : AppCompatActivity() {
    val items = arrayListOf<Person>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danh_ba)

        repeat(20) {
            val index = it + 1
            items.add(Person(
                index,
                "Nguyễn Tuấn Thành #$index",
                resources.getIdentifier("ic_launcher_background", "drawable", packageName),
                "$index"
            ))
            items.add(Person(
                index,
                "tnt2402 #$index",
                resources.getIdentifier("ic_launcher_background", "drawable", packageName),
                "$index"
            ))
        }

        val adapter = DanhBaItemAdapter(items)

        val recyclerView = findViewById<ListView>(R.id.recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setOnItemClickListener { _: AdapterView<*>, _: View, position : Int, _: Long ->
            val intent = Intent(this, DanhBaDetail::class.java)
            intent.putExtra("Item", items[position])
            startActivity(intent)
        }

        registerForContextMenu(recyclerView)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_danhba_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val selectedItem = items[(item.menuInfo as AdapterView.AdapterContextMenuInfo).position]
        if (item.itemId == R.id.action_call) {
            Log.v("TAG", "Call $selectedItem")
            val intent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel: 0988842692")
            }
            startActivity(intent)
        } else if (item.itemId == R.id.action_sms) {
            Log.v("TAG", "SMS $selectedItem")
            val intent = Intent(Intent.ACTION_SEND, Uri.parse("smsto:0987654321"))
            intent.type = "text/plain"
            intent.putExtra("sms_body", "Content")
            startActivity(intent)
        } else if (item.itemId == R.id.action_email) {
            Log.v("TAG", "Email $selectedItem")
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // Only email apps handle this.
                putExtra(Intent.EXTRA_EMAIL, "hoangdanhquan@yahoo.com")
                putExtra(Intent.EXTRA_SUBJECT, "Hello")
            }
            startActivity(intent)
        }
        return super.onContextItemSelected(item)
    }


}