package com.ezsoftware.ezplans.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ezsoftware.ezplans.preferences.UsuarioRegistrado

@Composable
fun TabInfoBasicaActiv(
    titulo: String,
    errorTitulo: Boolean,
    detalles: String,
    onTituloCambio: (String) -> Unit,
    onDetalleCambio: (String) -> Unit,
    onErrorTituloCambio: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 20.dp)
    ) {
        Titulo("Información Básica")
        TextoPeq("Información básica de la actividad")
        Spacer(modifier = Modifier.size(25.dp))

        SubTitulo("Titulo de la actividad", false)
        OutlinedTextForms(
            valor = titulo,
            label = "Plan sano",
            hayError = errorTitulo,
            singleLine = true,
            onValorChange = {
                onTituloCambio(it)
                onErrorTituloCambio(false)
            }
        )

        Spacer(modifier = Modifier.size(15.dp))
        SubTitulo("Detalles (Opcional)", false)
        OutlinedTextForms(
            valor = detalles,
            label = "Detalles sanos",
            alto = 120.dp,
            singleLine = false,
            maxLines = 5,
            onValorChange = {
                onDetalleCambio(it)
            }
        )

        Spacer(modifier = Modifier.size(18.dp))
        Row (horizontalArrangement = Arrangement.spacedBy(8.dp)){
            Icon(imageVector = Icons.Default.Info, "informacion")
            TextoPeq("El gasto total se calculará automáticamente sumando las aportaciones de cada miembro en el siguiente paso")
        }
    }
}

@Composable
fun TabParticipantesActiv(
    usuariosDefault: List<UsuarioRegistrado>,
    usuariosSelect: List<Int>,
    divicionIgual: Boolean,
    nombreUsuSelect: List<String>,
    onUsuariosSelectChange: (List<Int>) -> Unit,
    onDivicionIgual: (Boolean) -> Unit,
    onNombreUsuSelect: (List<String>) -> Unit
) {
    Column (
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 20.dp)
    ){
        Titulo("Participantes")
        TextoPeq("Selecciona quién partició en esta actividad")
        Spacer(modifier = Modifier.size(25.dp))

        SubTitulo("Seleccionar Participantes", false)
        Spacer(modifier = Modifier.size(10.dp))
        Column (
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 6.dp)
        ) {
            usuariosDefault.forEach { usuario ->
                CardUsuariosDisp(
                    nombre = "${usuario.nombre_usuario} ${usuario.apellidos_usuario}",
                    telefono = usuario.celular_usuario,
                    checked = usuario.id_usuario in usuariosSelect,
                    onCheckedChange = { checked ->
                        onUsuariosSelectChange(
                            if (checked) {
                                usuariosSelect + usuario.id_usuario
                            } else {
                                usuariosSelect - usuario.id_usuario
                            }
                        )
                        onNombreUsuSelect(
                            if (checked) {
                                nombreUsuSelect + "${usuario.nombre_usuario} ${usuario.apellidos_usuario}"
                            } else {
                                nombreUsuSelect + "${usuario.nombre_usuario} ${usuario.apellidos_usuario}"
                            }
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.size(18.dp))

        SubTitulo("Método de División", false)
        Spacer(modifier = Modifier.size(5.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ) {
            Box(modifier = Modifier.weight(1f)){
                CardSeleccionMetodo(
                    texto = "División Igual",
                    descripcion = "Dividir entre todos por igual",
                    seleccionado = divicionIgual,
                    onClick = { onDivicionIgual(true) }
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Box(modifier = Modifier.weight(1f)){
                CardSeleccionMetodo(
                    texto = "Montos Personalizados",
                    descripcion = "Asignar montos personalizados",
                    seleccionado = !divicionIgual,
                    onClick = { onDivicionIgual(false) }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TabContribuciones(
    gastoTotal: Int,
    usuariosSelect: List<Int>,
    nombreUsuSelect: List<String>
){
    Column (
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 20.dp)
    ){
        Titulo("Contribuciones")
        TextoPeq("Define cuánto pagó cada persona")
        Spacer(modifier = Modifier.size(25.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row {
                SubTitulo("Contribuciones y Pagos", false)
            }
            Row {
                SubTitulo("Gasto Total: $1000.00", true)
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Column (
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 6.dp)
        ) {
            usuariosSelect.forEach { usuario ->
                CardContribuciones()
            }
        }
    }
}

@Composable
fun CardContribuciones(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(15.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row (verticalAlignment = Alignment.CenterVertically){
                Imagen("placeholder", 50)
                Spacer(modifier = Modifier.size(10.dp))
                Column {
                    Texto("nombre")
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF4CAF50).copy(alpha = 0.3f),
                        tonalElevation = 1.dp,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Box(Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                            TextoPeq("Le deben $10.00")
                        }
                    }/*
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF44336).copy(alpha = 0.3f),
                        tonalElevation = 1.dp,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Box(Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                            TextoPeq("Debe $10.00")
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF9E9E9E).copy(alpha = 0.3f),
                        tonalElevation = 1.dp,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Box(Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                            TextoPeq("Sin deudas $10.00")
                        }
                    }*/
                }
            }
            Spacer(modifier = Modifier.size(10.dp))

            Row(Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    TextoPeq("Cantidad pagada")
                    var valorSeleccionado by remember { mutableStateOf(0) }

                    SelectorDeNumeros(
                        valor = valorSeleccionado,
                        rangoMin = 1,
                        onValorCambiado = { valorSeleccionado = it }
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    TextoPeq("Debe pagar")
                    var valorSeleccionado by remember { mutableStateOf(0) }

                    SelectorDeNumeros(
                        valor = valorSeleccionado,
                        rangoMin = 1,
                        onValorCambiado = { valorSeleccionado = it }
                    )
                }
            }

            TextoPeq("¿A quién le debe?")
            val listaDeUsuarios = listOf("Ana", "Luis", "Carlos", "María")
            var usuarioSeleccionado by rememberSaveable { mutableStateOf<Int?>(null) }

            SingleUserDropdown(
                opciones = listaDeUsuarios,
                seleccionado = usuarioSeleccionado,
                onSeleccionCambio = { usuarioSeleccionado = it }
            )

        }
    }
}

@Composable
fun TabResumenDeudas(){
    Column (
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 20.dp)
    ){
        Titulo("Resumen de Deudas")
        TextoPeq("Deudas generadas de la actividad")
        Spacer(modifier = Modifier.size(25.dp))

        Column (
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 6.dp)
        ) {
            //usuariosSelect.forEach { usuario ->
                //CardResumenDeudas()
            //}
        }

        CardResumenActividad()
    }
}

@Composable
fun CardSeleccionMetodo(
    texto: String,
    descripcion: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val textColor = if (seleccionado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Imagen("placeholder", 25)
            Texto(texto, false, textColor, Modifier.fillMaxWidth(), TextAlign.Center)
            TextoPeq(descripcion, textColor, Modifier.fillMaxWidth(), TextAlign.Center)
        }
    }
}

@Composable
fun CardResumenDeudas(
    nombre: String,
    telefono: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(15.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Imagen("placeholder", 50)
            Spacer(modifier = Modifier.size(5.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Texto(nombre, true)
                TextoPeq(telefono)
            }
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun CardResumenActividad(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(15.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SubTitulo("Reumen de la Actividad", true)
            Row {
                Column(modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Texto("Titulo")
                    Texto("Plan sano", true)
                }
                Column (modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)){
                    Texto("Gasto Total")
                    Texto("$30.00", true)
                }
            }
            Row {
                Column (modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)){
                    Texto("Participantes")
                    Texto("3 personas", true)
                }
                Column (modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)){
                    Texto("Deudas generadas")
                    Texto("1 transacción", true)
                }
            }
        }
    }
}
