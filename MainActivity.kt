package com.hphunter.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

private val Bg = Color(0xFF090F1D)
private val Panel = Color(0xFF141F33)
private val Lime = Color(0xFFB8F36B)
private val Muted = Color(0xFF9AA8BE)
private fun rupiah(v: Long) = "Rp" + NumberFormat.getNumberInstance(Locale("id","ID")).format(v)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HPHunterApp() }
    }
}

@Composable
fun HPHunterApp() {
    val context = LocalContext.current
    var tab by remember { mutableStateOf(0) }
    var brand by remember { mutableStateOf("Semua Android") }
    var keyword by remember { mutableStateOf("") }
    var maxPrice by remember { mutableStateOf("1500000") }
    var area by remember { mutableStateOf("Sekitar saya") }
    var buy by remember { mutableStateOf("1500000") }
    var sell by remember { mutableStateOf("1800000") }
    var travel by remember { mutableStateOf("30000") }
    var repair by remember { mutableStateOf("100000") }
    var note by remember { mutableStateOf("Belum ada data stok tersimpan.") }
    val profit = (sell.toLongOrNull() ?: 0L) - (buy.toLongOrNull() ?: 0L) -
            (travel.toLongOrNull() ?: 0L) - (repair.toLongOrNull() ?: 0L)

    MaterialTheme(colorScheme = darkColorScheme(
        background = Bg, surface = Panel, primary = Lime,
        onPrimary = Bg, onBackground = Color.White, onSurface = Color.White
    )) {
        Scaffold(containerColor = Bg, bottomBar = {
            NavigationBar(containerColor = Panel) {
                NavigationBarItem(selected = tab == 0, onClick = { tab = 0 },
                    icon = { Icon(Icons.Default.Search, null) }, label = { Text("Cari") })
                NavigationBarItem(selected = tab == 1, onClick = { tab = 1 },
                    icon = { Icon(Icons.Default.Calculate, null) }, label = { Text("Kalkulator") })
                NavigationBarItem(selected = tab == 2, onClick = { tab = 2 },
                    icon = { Icon(Icons.Default.Inventory2, null) }, label = { Text("Stok") })
            }
        }) { padding ->
            LazyColumn(Modifier.fillMaxSize().padding(padding).padding(horizontal = 18.dp)) {
                item {
                    Spacer(Modifier.height(22.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(color = Lime, shape = RoundedCornerShape(14.dp)) {
                            Text("H", Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                color = Bg, fontSize = 24.sp, fontWeight = FontWeight.Black)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("HP HUNTER", fontSize = 22.sp, fontWeight = FontWeight.Black)
                            Text("MARKETPLACE DEAL ASSISTANT", color = Lime, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(Modifier.height(18.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Metric("MODAL", "Rp5 juta", Modifier.weight(1f))
                        Metric("TARGET LABA", "≥ Rp100 rb", Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(18.dp))
                }
                when (tab) {
                    0 -> {
                        item {
                            Heading("Temukan peluang HP")
                            Text("Atur filter, lalu buka hasil pencarian Marketplace.", color = Muted, fontSize = 13.sp)
                            Spacer(Modifier.height(12.dp))
                            DarkField("Merek", brand, { brand = it }, listOf("Semua Android","Samsung","Xiaomi / Redmi","POCO","OPPO","vivo","Infinix","Realme","TECNO"))
                            DarkField("Tipe / kata kunci", keyword, { keyword = it }, placeholder = "Contoh: Redmi Note 12, BU")
                            DarkField("Harga maksimal (Rp)", maxPrice, { maxPrice = it }, keyboard = true)
                            DarkField("Area COD", area, { area = it }, placeholder = "Kecamatan / kota")
                            Button(onClick = {
                                val q = listOf(brand.takeUnless { it == "Semua Android" }, keyword, area)
                                    .filterNotNull().filter { it.isNotBlank() }.joinToString(" ")
                                val uri = Uri.parse("https://www.facebook.com/marketplace/search/?query=" + Uri.encode(q.ifBlank { "handphone bekas" }))
                                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Lime, contentColor = Bg)) {
                                Icon(Icons.Default.OpenInNew, null); Spacer(Modifier.width(8.dp)); Text("BUKA FACEBOOK MARKETPLACE", fontWeight = FontWeight.Bold)
                            }
                            Spacer(Modifier.height(12.dp))
                            Card(colors = CardDefaults.cardColors(containerColor = Panel)) {
                                Text("Catatan: pencarian ini membuka Marketplace. Aplikasi belum memindai iklan secara otomatis. Integrasi pemantauan memerlukan sumber data dan izin resmi yang tersedia.", Modifier.padding(14.dp), color = Muted, fontSize = 12.sp)
                            }
                        }
                    }
                    1 -> {
                        item {
                            Heading("Kalkulator keuntungan")
                            DarkField("Harga beli (Rp)", buy, { buy = it }, keyboard = true)
                            DarkField("Estimasi harga jual (Rp)", sell, { sell = it }, keyboard = true)
                            DarkField("Transport / COD (Rp)", travel, { travel = it }, keyboard = true)
                            DarkField("Cadangan servis (Rp)", repair, { repair = it }, keyboard = true)
                            Card(colors = CardDefaults.cardColors(containerColor = Panel), modifier = Modifier.fillMaxWidth()) {
                                Column(Modifier.padding(18.dp)) {
                                    Text("ESTIMASI LABA BERSIH", color = Muted, fontSize = 11.sp)
                                    Text(rupiah(profit), color = if (profit >= 100000) Lime else Color(0xFFFFA58F), fontSize = 28.sp, fontWeight = FontWeight.Black)
                                    Text(if (profit >= 100000) "Memenuhi target laba minimum." else "Belum mencapai target Rp100.000.", color = Muted, fontSize = 12.sp)
                                    Spacer(Modifier.height(12.dp))
                                    Button(onClick = { note = "Rencana: ${rupiah(buy.toLongOrNull() ?: 0)} → target jual ${rupiah(sell.toLongOrNull() ?: 0)} | estimasi laba ${rupiah(profit)}" },
                                        colors = ButtonDefaults.buttonColors(containerColor = Lime, contentColor = Bg)) { Text("SIMPAN RINGKASAN") }
                                }
                            }
                        }
                    }
                    else -> item {
                        Heading("Stok & rencana pembelian")
                        Card(colors = CardDefaults.cardColors(containerColor = Panel), modifier = Modifier.fillMaxWidth()) {
                            Text(note, Modifier.padding(16.dp), color = Muted)
                        }
                        Spacer(Modifier.height(10.dp))
                        Text("Versi awal menyimpan satu ringkasan sementara. Pengembangan berikutnya akan menambahkan daftar stok persisten, status dibeli/terjual, dan ekspor data.", color = Muted, fontSize = 12.sp)
                    }
                }
                item { Spacer(Modifier.height(30.dp)) }
            }
        }
    }
}

@Composable
fun Metric(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(14.dp)) {
            Text(label, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(value, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }
    }
}
@Composable
fun Heading(t: String) { Text(t, fontSize = 19.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(10.dp)) }

@Composable
fun DarkField(label: String, value: String, onChange: (String) -> Unit,
              options: List<String>? = null, placeholder: String = "", keyboard: Boolean = false) {
    Column(Modifier.fillMaxWidth().padding(bottom = 11.dp)) {
        Text(label, color = Muted, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(5.dp))
        if (options != null) {
            var expanded by remember { mutableStateOf(false) }
            Box {
                OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)) {
                    Text(value, modifier = Modifier.weight(1f)); Icon(Icons.Default.ArrowDropDown, null)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    options.forEach { opt -> DropdownMenuItem(text = { Text(opt) }, onClick = { onChange(opt); expanded = false }) }
                }
            }
        } else {
            OutlinedTextField(value, onChange, modifier = Modifier.fillMaxWidth(), singleLine = true,
                placeholder = { Text(placeholder, color = Muted) },
                keyboardOptions = KeyboardOptions(keyboardType = if (keyboard) KeyboardType.Number else KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                    focusedBorderColor = Lime, unfocusedBorderColor = Color(0xFF40506A),
                    cursorColor = Lime
                ))
        }
    }
}
