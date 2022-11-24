CREATE SCHEMA gymanager;

SET search_path TO gymanager;
-- gymanager.bloque definition

-- Drop table

-- DROP TABLE bloque;

CREATE TABLE bloque (
	id_bloque int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	nombre varchar(40) NOT NULL,
	descripcion varchar(100) NULL DEFAULT ''::character varying,
	CONSTRAINT bloque_nombre_key UNIQUE (nombre),
	CONSTRAINT bloque_pkey PRIMARY KEY (id_bloque)
);


-- gymanager.estado_seguimiento definition

-- Drop table

-- DROP TABLE estado_seguimiento;

CREATE TABLE estado_seguimiento (
	id_estado_seguimiento int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	descripcion varchar(50) NOT NULL,
	puntaje int4 NOT NULL,
	color varchar NOT NULL,
	CONSTRAINT estado_seguimiento_pkey PRIMARY KEY (id_estado_seguimiento)
);


-- gymanager.herramienta definition

-- Drop table

-- DROP TABLE herramienta;

CREATE TABLE herramienta (
	id_herramienta int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	nombre varchar(50) NOT NULL,
	descripcion varchar(200) NULL,
	CONSTRAINT herramienta_nombre_u UNIQUE (nombre),
	CONSTRAINT herramienta_pkey PRIMARY KEY (id_herramienta)
);


-- gymanager.objetivo definition

-- Drop table

-- DROP TABLE objetivo;

CREATE TABLE objetivo (
	id_objetivo int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	objetivo varchar(50) NOT NULL,
	CONSTRAINT objetivo_pkey PRIMARY KEY (id_objetivo)
);


-- gymanager.permiso definition

-- Drop table

-- DROP TABLE permiso;

CREATE TABLE permiso (
	id_permiso int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	descripcion varchar(255) NULL,
	nombre_permiso varchar(255) NULL,
	objeto varchar(255) NULL,
	CONSTRAINT permiso_pkey PRIMARY KEY (id_permiso)
);


-- gymanager.rol definition

-- Drop table

-- DROP TABLE rol;

CREATE TABLE rol (
	id_rol int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	nombre_rol varchar(255) NULL,
	CONSTRAINT rol_pkey PRIMARY KEY (id_rol)
);


-- gymanager.sexo definition

-- Drop table

-- DROP TABLE sexo;

CREATE TABLE sexo (
	id_sexo int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	sexo varchar(50) NOT NULL,
	CONSTRAINT sexo_pkey PRIMARY KEY (id_sexo)
);


-- gymanager.tipo_documento definition

-- Drop table

-- DROP TABLE tipo_documento;

CREATE TABLE tipo_documento (
	id_tipo_documento int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	tipo varchar(30) NOT NULL,
	CONSTRAINT tipo_documento_pkey PRIMARY KEY (id_tipo_documento)
);


-- gymanager.tipo_ejercicio definition

-- Drop table

-- DROP TABLE tipo_ejercicio;

CREATE TABLE tipo_ejercicio (
	id_tipo_ejercicio int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	nombre varchar(50) NOT NULL,
	descripcion varchar(200) NULL,
	CONSTRAINT tipo_ejercicio_nombre_u UNIQUE (nombre),
	CONSTRAINT tipo_ejercicio_pkey PRIMARY KEY (id_tipo_ejercicio)
);


-- gymanager.ejercicio definition

-- Drop table

-- DROP TABLE ejercicio;

CREATE TABLE ejercicio (
	id_ejercicio int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	nombre varchar(50) NOT NULL,
	id_tipo_ejercicio int4 NOT NULL,
	video varchar(100) NULL,
	fecha_baja date NULL,
	CONSTRAINT ejercicio_pkey PRIMARY KEY (id_ejercicio),
	CONSTRAINT ejercicio_id_tipo_ejercicio_fk FOREIGN KEY (id_tipo_ejercicio) REFERENCES tipo_ejercicio(id_tipo_ejercicio)
);


-- gymanager.ejercicio_por_herramienta definition

-- Drop table

-- DROP TABLE ejercicio_por_herramienta;

CREATE TABLE ejercicio_por_herramienta (
	id_herramienta int8 NOT NULL,
	id_ejercicio int8 NOT NULL,
	CONSTRAINT ejercicio_por_herramienta_pkey PRIMARY KEY (id_herramienta, id_ejercicio),
	CONSTRAINT ejercicio_por_herramienta_id_ejercicio_fk FOREIGN KEY (id_ejercicio) REFERENCES ejercicio(id_ejercicio) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT ejercicio_por_herramienta_id_herramienta_fk FOREIGN KEY (id_herramienta) REFERENCES herramienta(id_herramienta) ON DELETE CASCADE ON UPDATE CASCADE
);


-- gymanager.paso definition

-- Drop table

-- DROP TABLE paso;

CREATE TABLE paso (
	id_paso int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	numero_paso int4 NOT NULL,
	contenido text NULL,
	imagen text NULL,
	id_ejercicio int8 NOT NULL,
	CONSTRAINT paso_pkey PRIMARY KEY (id_paso),
	CONSTRAINT paso_id_ejercicio_fk FOREIGN KEY (id_ejercicio) REFERENCES ejercicio(id_ejercicio) ON DELETE CASCADE ON UPDATE CASCADE
);


-- gymanager.rol_por_permiso definition

-- Drop table

-- DROP TABLE rol_por_permiso;

CREATE TABLE rol_por_permiso (
	id_rol int8 NOT NULL,
	id_permiso int8 NOT NULL,
	CONSTRAINT rol_por_permiso_id_permiso_fk FOREIGN KEY (id_permiso) REFERENCES permiso(id_permiso) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT rol_por_permiso_id_rol_fk FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE ON UPDATE CASCADE
);


-- gymanager.usuario definition

-- Drop table

-- DROP TABLE usuario;

CREATE TABLE usuario (
	id_usuario int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	numero_documento int8 NOT NULL,
	id_tipo_documento int4 NOT NULL,
	nombre varchar(50) NOT NULL,
	apellido varchar(50) NOT NULL,
	id_sexo int4 NULL,
	pass varchar(255) NOT NULL,
	fecha_alta date NULL,
	fecha_baja date NULL,
	mail varchar(100) NOT NULL,
	celular int8 NULL,
	CONSTRAINT usuario_id_tipo_documento_numero_documento_u UNIQUE (id_tipo_documento, numero_documento),
	CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario),
	CONSTRAINT usuario_id_sexo_fk FOREIGN KEY (id_sexo) REFERENCES sexo(id_sexo),
	CONSTRAINT usuario_id_tipo_documento_fk FOREIGN KEY (id_tipo_documento) REFERENCES tipo_documento(id_tipo_documento)
);


-- gymanager.usuario_por_rol definition

-- Drop table

-- DROP TABLE usuario_por_rol;

CREATE TABLE usuario_por_rol (
	id_usuario int8 NOT NULL,
	id_rol int8 NOT NULL,
	CONSTRAINT usuario_por_rol_id_rol_fk FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT usuario_por_rol_id_usuario_fk FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE
);


-- gymanager.avatar definition

-- Drop table

-- DROP TABLE avatar;

CREATE TABLE avatar (
	id_usuario int8 NOT NULL,
	imagen text NULL,
	CONSTRAINT avatar_pkey PRIMARY KEY (id_usuario),
	CONSTRAINT avatar_id_usuario_fk FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE
);


-- gymanager.cliente definition

-- Drop table

-- DROP TABLE cliente;

CREATE TABLE cliente (
	id_cliente int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	id_usuario int8 NOT NULL,
	id_objetivo int8 NOT NULL,
	direccion varchar(255) NULL,
	fecha_nacimiento date NOT NULL,
	observaciones varchar(255) NULL,
	fecha_baja date NULL,
	CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente),
	CONSTRAINT cliente_id_objetivo_fk FOREIGN KEY (id_objetivo) REFERENCES objetivo(id_objetivo),
	CONSTRAINT cliente_id_usuario_fk FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);


-- gymanager.matricula definition

-- Drop table

-- DROP TABLE matricula;

CREATE TABLE matricula (
	id_matricula int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	id_cliente int8 NOT NULL,
	fecha_pago date NOT NULL DEFAULT CURRENT_DATE,
	fecha_inicio date NOT NULL DEFAULT CURRENT_DATE,
	fecha_vencimiento date NOT NULL DEFAULT CURRENT_DATE,
	cantidad_meses int4 NOT NULL,
	cantidad_dias_semana int4 NULL,
	CONSTRAINT matricula_pkey PRIMARY KEY (id_matricula),
	CONSTRAINT matricula_cliente_fk FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);


-- gymanager.medidas_cliente definition

-- Drop table

-- DROP TABLE medidas_cliente;

CREATE TABLE medidas_cliente (
	id_medidas int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	id_cliente int8 NOT NULL,
	fecha date NOT NULL DEFAULT CURRENT_TIMESTAMP,
	peso float4 NULL,
	altura float4 NULL,
	cervical float4 NULL,
	dorsal float4 NULL,
	lumbar float4 NULL,
	coxal_pelvica float4 NULL,
	cadera float4 NULL,
	muslos_izq float4 NULL,
	muslos_der float4 NULL,
	rodillas_izq float4 NULL,
	rodillas_der float4 NULL,
	gemelos_izq float4 NULL,
	gemelos_der float4 NULL,
	brazo_izq float4 NULL,
	brazo_der float4 NULL,
	foto text NULL,
	CONSTRAINT medidas_cliente_pkey PRIMARY KEY (id_medidas),
	CONSTRAINT medidas_cliente_cliente_fk FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);


-- gymanager.plan definition

-- Drop table

-- DROP TABLE plan;

CREATE TABLE plan (
	id_plan int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	id_usuario_profesor int8 NOT NULL,
	id_cliente int8 NOT NULL,
	id_objetivo int4 NOT NULL,
	fecha_desde date NOT NULL DEFAULT CURRENT_DATE,
	fecha_hasta date NULL,
	descripcion varchar(1000) NULL,
	fecha_eliminado date NULL,
	observacion_cliente varchar NULL DEFAULT ''::character varying,
	id_estado_seguimiento int4 NULL,
	CONSTRAINT plan_pkey PRIMARY KEY (id_plan),
	CONSTRAINT plan_cliente_fk FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
	CONSTRAINT plan_objetivo_fk FOREIGN KEY (id_objetivo) REFERENCES objetivo(id_objetivo),
	CONSTRAINT plan_usuario_fk FOREIGN KEY (id_usuario_profesor) REFERENCES usuario(id_usuario)
);


-- gymanager.micro_plan definition

-- Drop table

-- DROP TABLE micro_plan;

CREATE TABLE micro_plan (
	id_micro_plan int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	id_plan int8 NULL,
	nombre varchar(50) NOT NULL,
	es_template bool NOT NULL DEFAULT false,
	numero_orden int4 NULL,
	fecha_baja date NULL,
	CONSTRAINT micro_plan_pkey PRIMARY KEY (id_micro_plan),
	CONSTRAINT micro_plan_plan_fk FOREIGN KEY (id_plan) REFERENCES plan(id_plan)
);


-- gymanager.observacion_micro_plan definition

-- Drop table

-- DROP TABLE observacion_micro_plan;

CREATE TABLE observacion_micro_plan (
	id_observacion_micro_plan int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	id_micro_plan int8 NOT NULL,
	numero_semana int4 NOT NULL,
	observacion varchar(500) NULL DEFAULT ''::character varying,
	CONSTRAINT observacion_micro_plan_pkey PRIMARY KEY (id_observacion_micro_plan),
	CONSTRAINT observacion_micro_plan_micro_plan_fk FOREIGN KEY (id_micro_plan) REFERENCES micro_plan(id_micro_plan)
);


-- gymanager.rutina definition

-- Drop table

-- DROP TABLE rutina;

CREATE TABLE rutina (
	id_rutina int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	id_micro_plan int8 NOT NULL,
	nombre varchar(50) NULL DEFAULT ''::character varying,
	es_template bool NOT NULL DEFAULT false,
	CONSTRAINT rutina_pkey PRIMARY KEY (id_rutina),
	CONSTRAINT rutina_micro_plan_fk FOREIGN KEY (id_micro_plan) REFERENCES micro_plan(id_micro_plan)
);


-- gymanager.seguimiento_fin_dia definition

-- Drop table

-- DROP TABLE seguimiento_fin_dia;

CREATE TABLE seguimiento_fin_dia (
	id_seguimiento_fin_dia int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	id_rutina int8 NOT NULL,
	fecha_carga date NOT NULL,
	observacion varchar(300) NULL,
	id_estado_seguimiento int4 NOT NULL,
	CONSTRAINT seguimiento_fin_dia_pkey PRIMARY KEY (id_seguimiento_fin_dia),
	CONSTRAINT seguimiento_fin_dia_estado_seguimiento_fk FOREIGN KEY (id_estado_seguimiento) REFERENCES estado_seguimiento(id_estado_seguimiento),
	CONSTRAINT seguimiento_fin_dia_rutina_fk FOREIGN KEY (id_rutina) REFERENCES rutina(id_rutina)
);


-- gymanager.ejercicio_aplicado definition

-- Drop table

-- DROP TABLE ejercicio_aplicado;

CREATE TABLE ejercicio_aplicado (
	id_ejercicio_aplicado int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	id_rutina int8 NOT NULL,
	id_ejercicio int8 NOT NULL,
	id_bloque int8 NOT NULL,
	series int4 NULL,
	repeticiones int4 NULL,
	pausa_micro varchar(20) NULL,
	pausa_macro varchar(20) NULL,
	carga float4 NULL,
	tiempo float4 NULL,
	es_template bool NOT NULL DEFAULT false,
	CONSTRAINT ejercicio_aplicado_pkey PRIMARY KEY (id_ejercicio_aplicado),
	CONSTRAINT ejercicio_aplicado_ejercicio_fk FOREIGN KEY (id_ejercicio) REFERENCES ejercicio(id_ejercicio),
	CONSTRAINT ejercicio_aplicado_id_bloque_fk FOREIGN KEY (id_bloque) REFERENCES bloque(id_bloque),
	CONSTRAINT ejercicio_aplicado_rutina_fk FOREIGN KEY (id_rutina) REFERENCES rutina(id_rutina)
);


-- gymanager.seguimiento_ejercicio definition

-- Drop table

-- DROP TABLE seguimiento_ejercicio;

CREATE TABLE seguimiento_ejercicio (
	id_seguimiento_ejercicio int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	fecha_carga date NOT NULL,
	id_plan int8 NOT NULL,
	id_ejercicio_aplicado int8 NOT NULL,
	carga_real float4 NULL,
	tiempo_real float4 NULL,
	observacion varchar(300) NULL,
	CONSTRAINT seguimiento_ejercicio_pkey PRIMARY KEY (id_seguimiento_ejercicio),
	CONSTRAINT seguimiento_ejercicio_ejercicio_aplicado_fk FOREIGN KEY (id_ejercicio_aplicado) REFERENCES ejercicio_aplicado(id_ejercicio_aplicado),
	CONSTRAINT seguimiento_ejercicio_plan_fk FOREIGN KEY (id_plan) REFERENCES plan(id_plan)
);