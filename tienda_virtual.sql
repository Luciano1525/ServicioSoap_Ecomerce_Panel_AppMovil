-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3307
-- Tiempo de generación: 12-04-2024 a las 03:31:09
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tienda_virtual`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id` int(11) NOT NULL,
  `categoria` varchar(100) NOT NULL,
  `imagen` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id`, `categoria`, `imagen`) VALUES
(1, 'Bytecard', 'https://robohash.org/sapientesitminus.png?size=400x400&set=set1'),
(2, 'Transcof', 'https://robohash.org/suntsuntquis.png?size=400x400&set=set1'),
(3, 'Job', 'https://robohash.org/temporeexplicaboeum.png?size=400x400&set=set1'),
(4, 'Lotlux', 'https://robohash.org/sintpraesentiumdolorem.png?size=400x400&set=set1'),
(5, 'Sonsing', 'https://robohash.org/quiscumad.png?size=400x400&set=set1'),
(6, 'Y-Solowarm', 'https://robohash.org/liberoautemexpedita.png?size=400x400&set=set1'),
(7, 'Job', 'https://robohash.org/temporanemoofficiis.png?size=400x400&set=set1'),
(8, 'Sub-Ex', 'https://robohash.org/pariaturvoluptasducimus.png?size=400x400&set=set1'),
(9, 'Stronghold', 'https://robohash.org/mollitiaplaceatlaborum.png?size=400x400&set=set1'),
(10, 'Bitwolf', 'https://robohash.org/quibusdamvoluptatibusrem.png?size=400x400&set=set1'),
(11, 'Solarbreeze', 'https://robohash.org/voluptatemenimnon.png?size=400x400&set=set1'),
(12, 'Cookley', 'https://robohash.org/autdictamolestiae.png?size=400x400&set=set1'),
(13, 'Flexidy', 'https://robohash.org/molestiasharumsapiente.png?size=400x400&set=set1'),
(14, 'Tresom', 'https://robohash.org/quiaaliasquam.png?size=400x400&set=set1'),
(15, 'Hatity', 'https://robohash.org/laudantiumfugadeserunt.png?size=400x400&set=set1'),
(16, 'Quo Lux', 'https://robohash.org/voluptaslaborevoluptates.png?size=400x400&set=set1'),
(17, 'Ventosanzap', 'https://robohash.org/totamsiterror.png?size=400x400&set=set1'),
(18, 'Cardguard', 'https://robohash.org/abdoloremquibusdam.png?size=400x400&set=set1'),
(19, 'Tres-Zap', 'https://robohash.org/delectusipsapraesentium.png?size=400x400&set=set1'),
(20, 'Domainer', 'https://robohash.org/nesciuntexplicabosapiente.png?size=400x400&set=set1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `correo` varchar(80) NOT NULL,
  `clave` varchar(100) NOT NULL,
  `perfil` varchar(100) NOT NULL DEFAULT 'default.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_pedidos`
--

CREATE TABLE `detalle_pedidos` (
  `id` int(11) NOT NULL,
  `producto` varchar(255) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id` int(11) NOT NULL,
  `id_transaccion` varchar(80) NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `estado` varchar(30) NOT NULL,
  `fecha` datetime NOT NULL,
  `email` varchar(80) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `ciudad` varchar(50) NOT NULL,
  `email_user` varchar(80) NOT NULL,
  `proceso` enum('1','2','3') NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `descripcion` longtext NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `imagen` varchar(150) NOT NULL,
  `id_categoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `nombre`, `descripcion`, `precio`, `cantidad`, `imagen`, `id_categoria`) VALUES
(1, 'Primer Producto', 'Bypass Cecum to Cecum with Synthetic Substitute, Percutaneous Endoscopic Approach', 626.18, 9, 'https://robohash.org/doloremearumprovident.png?size=800x800&set=set1', 1),
(2, 'Icecream Bar - Del Monte', 'Insertion of Pedicle-Based Spinal Stabilization Device into Cervicothoracic Vertebral Joint, Percutaneous Endoscopic Approach', 976.31, 56, 'https://robohash.org/nequerepudiandaevelit.png?size=800x800&set=set1', 2),
(3, 'Chevril', 'Destruction of Left Vas Deferens, Open Approach', 634.27, 5821, 'https://robohash.org/quiinciduntanimi.png?size=800x800&set=set1', 3),
(4, 'Flounder - Fresh', 'High Dose Rate (HDR) Brachytherapy of Bladder using Californium 252 (Cf-252)', 891.74, 17449, 'https://robohash.org/aliasautvoluptatem.png?size=800x800&set=set1', 4),
(5, 'Cups 10oz Trans', 'Dilation of Right Axillary Artery, Bifurcation, with Two Intraluminal Devices, Percutaneous Endoscopic Approach', 894.20, 3036, 'https://robohash.org/similiquealiasasperiores.png?size=800x800&set=set1', 5),
(6, 'Veal - Eye Of Round', 'Extirpation of Matter from Right Popliteal Artery, Percutaneous Endoscopic Approach', 427.30, 24449, 'https://robohash.org/sintconsequaturautem.png?size=800x800&set=set1', 6),
(7, 'Beer - Molson Excel', 'Removal of Nonautologous Tissue Substitute from Left Lower Extremity, Percutaneous Approach', 479.50, 1232, 'https://robohash.org/exadsed.png?size=800x800&set=set1', 7),
(8, 'Wine - Red, Gamay Noir', 'Destruction of Left Large Intestine, Via Natural or Artificial Opening', 878.28, 86934, 'https://robohash.org/temporibussuscipitet.png?size=800x800&set=set1', 8),
(9, 'Producto Final de la Pag 1', 'Repair Nose, External Approach', 427.41, 442, 'https://robohash.org/sequiexcepturireprehenderit.png?size=800x800&set=set1', 9),
(10, 'Maple Syrup', 'Bypass Superior Vena Cava to Left Pulmonary Vein, Open Approach', 644.97, 8, 'https://robohash.org/minimaquiplaceat.png?size=800x800&set=set1', 10),
(11, 'Mustard - Dijon', 'Drainage of Bladder, Percutaneous Endoscopic Approach', 581.77, 87683, 'https://robohash.org/corporisexcepturiin.png?size=800x800&set=set1', 11),
(12, 'Ice Cream Bar - Hageen Daz To', 'Supplement Anus with Synthetic Substitute, Open Approach', 212.96, 7107, 'https://robohash.org/autetlaboriosam.png?size=800x800&set=set1', 12),
(13, 'Bagel - Plain', 'Respiratory System, Reattachment', 992.65, 3473, 'https://robohash.org/ipsarecusandaeveritatis.png?size=800x800&set=set1', 13),
(14, 'Muffin Mix - Carrot', 'Drainage of Glossopharyngeal Nerve, Percutaneous Approach', 617.08, 1655, 'https://robohash.org/illumsuscipitex.png?size=800x800&set=set1', 14),
(15, 'Table Cloth 53x69 White', 'Insertion of Infusion Device into Right Vertebral Vein, Open Approach', 767.59, 9, 'https://robohash.org/utquisvoluptatem.png?size=800x800&set=set1', 15),
(16, 'Spice - Pepper Portions', 'Inspection of Fallopian Tube, Via Natural or Artificial Opening Endoscopic', 941.30, 48, 'https://robohash.org/asperioresomnisesse.png?size=800x800&set=set1', 16),
(17, 'Pork - Hock And Feet Attached', 'Physical Rehabilitation and Diagnostic Audiology, Rehabilitation, Motor and/or Nerve Function Assessment', 604.46, 56, 'https://robohash.org/sedsuscipittemporibus.png?size=800x800&set=set1', 17),
(18, 'Fork - Plastic', 'Insertion of Tissue Expander into Back Subcutaneous Tissue and Fascia, Open Approach', 558.83, 4037, 'https://robohash.org/dictaprovidentconsequatur.png?size=800x800&set=set1', 18),
(19, 'Nueva Pag', 'Bypass Stomach to Cutaneous, Open Approach', 583.45, 50, 'https://robohash.org/solutareprehenderitdistinctio.png?size=800x800&set=set1', 19),
(20, 'Clam - Cherrystone', 'Replacement of Right Lower Eyelid with Nonautologous Tissue Substitute, Percutaneous Approach', 701.41, 750, 'https://robohash.org/iustohicerror.png?size=800x800&set=set1', 20),
(21, 'Wine - Masi Valpolocell', 'Alteration of Lower Jaw with Autologous Tissue Substitute, Open Approach', 778.92, 52517, 'https://robohash.org/optioomnisin.png?size=800x800&set=set1', 5),
(22, 'Magnotta - Bel Paese White', 'Drainage of Lumbar Vertebral Joint with Drainage Device, Percutaneous Approach', 546.06, 2, 'https://robohash.org/autdignissimosdolores.png?size=800x800&set=set1', 8),
(23, 'Lobster - Base', 'Excision of Left Wrist Bursa and Ligament, Percutaneous Endoscopic Approach, Diagnostic', 813.52, 797, 'https://robohash.org/esteumdolor.png?size=800x800&set=set1', 11),
(24, 'Cookies - Fortune', 'Insertion of Intraluminal Device into Right Thyroid Artery, Percutaneous Approach', 529.74, 10, 'https://robohash.org/estutrerum.png?size=800x800&set=set1', 9),
(25, 'Beef - Bones, Marrow', 'Transfer Accessory Nerve to Facial Nerve, Open Approach', 791.42, 11, 'https://robohash.org/sedquaenostrum.png?size=800x800&set=set1', 14),
(26, 'Croissant, Raw - Mini', 'Extirpation of Matter from Right Ovary, Percutaneous Endoscopic Approach', 316.05, 24185, 'https://robohash.org/quitemporeest.png?size=800x800&set=set1', 3),
(27, 'Dikon', 'Dilation of Right Ureter with Intraluminal Device, Open Approach', 371.01, 176, 'https://robohash.org/doloresrerumadipisci.png?size=800x800&set=set1', 7),
(28, 'Carbonated Water - Peach', 'Destruction of Facial Muscle, Percutaneous Approach', 775.65, 5, 'https://robohash.org/doloresistevoluptatem.png?size=800x800&set=set1', 19),
(29, 'Chickhen - Chicken Phyllo', 'Reposition Azygos Vein, Open Approach', 558.69, 57878, 'https://robohash.org/maximenihilaliquam.png?size=800x800&set=set1', 10),
(30, 'Wine - White, Cooking', 'Release Right Carotid Body, Open Approach', 561.69, 25, 'https://robohash.org/quissimiliqueeligendi.png?size=800x800&set=set1', 15),
(31, 'Strawberries', 'Excision of Right Thorax Bursa and Ligament, Percutaneous Approach', 107.83, 1, 'https://robohash.org/eosquosut.png?size=800x800&set=set1', 14),
(32, 'Beef - Texas Style Burger', 'Excision of Left Thorax Tendon, Percutaneous Approach', 416.34, 0, 'https://robohash.org/voluptatumetperferendis.png?size=800x800&set=set1', 6),
(33, 'Pork - Backfat', 'Extirpation of Matter from Right Upper Leg Muscle, Percutaneous Endoscopic Approach', 558.21, 27765, 'https://robohash.org/pariaturetsunt.png?size=800x800&set=set1', 12),
(34, 'Cookies - Amaretto', 'Drainage of Left Frontal Bone, Percutaneous Endoscopic Approach', 445.29, 80705, 'https://robohash.org/remerrorexercitationem.png?size=800x800&set=set1', 7),
(35, 'Cake - Sheet Strawberry', 'Drainage of Right Humeral Shaft with Drainage Device, Percutaneous Endoscopic Approach', 217.32, 65, 'https://robohash.org/voluptasomnissaepe.png?size=800x800&set=set1', 16),
(36, 'Sauce - Chili', 'Drainage of Left Carotid Body, Percutaneous Approach, Diagnostic', 378.88, 52341, 'https://robohash.org/autemutratione.png?size=800x800&set=set1', 18),
(37, 'Ice Cream - Strawberry', 'Reattachment of Right Upper Lobe Bronchus, Open Approach', 534.01, 514, 'https://robohash.org/facilisaliquamvoluptatum.png?size=800x800&set=set1', 14),
(38, 'Scallops - U - 10', 'Replacement of Left Ankle Joint with Synthetic Substitute, Cemented, Open Approach', 985.46, 108, 'https://robohash.org/repudiandaequialias.png?size=800x800&set=set1', 15),
(39, 'Sauce - Balsamic Viniagrette', 'Supplement Right Hepatic Duct with Nonautologous Tissue Substitute, Open Approach', 238.14, 17544, 'https://robohash.org/aquisducimus.png?size=800x800&set=set1', 17),
(40, 'Octopus', 'Supplement Aortic Valve created from Truncal Valve with Nonautologous Tissue Substitute, Percutaneous Approach', 201.10, 7, 'https://robohash.org/liberoestmaxime.png?size=800x800&set=set1', 2),
(41, 'Pork - Tenderloin, Fresh', 'Reposition Left Thumb Phalanx with External Fixation Device, Open Approach', 237.48, 65, 'https://robohash.org/modinonconsequatur.png?size=800x800&set=set1', 4),
(42, 'Glass Clear 8 Oz', 'Supplement Right Popliteal Artery with Nonautologous Tissue Substitute, Percutaneous Approach', 686.82, 5100, 'https://robohash.org/autquaeratculpa.png?size=800x800&set=set1', 15),
(43, 'Artichokes - Knobless, White', 'Introduction of Local Anesthetic into Spinal Canal, Percutaneous Approach', 660.68, 79, 'https://robohash.org/voluptatibusevenietaccusantium.png?size=800x800&set=set1', 14),
(44, 'Swordfish Loin Portions', 'Restriction of Left Internal Iliac Artery with Extraluminal Device, Percutaneous Endoscopic Approach', 961.78, 578, 'https://robohash.org/sintnemoeum.png?size=800x800&set=set1', 10),
(45, 'Puree - Raspberry', 'Dilation of Right Foot Artery, Bifurcation, with Three Drug-eluting Intraluminal Devices, Percutaneous Approach', 512.88, 5156, 'https://robohash.org/errorvoluptatumalias.png?size=800x800&set=set1', 18),
(46, 'Ocean Spray - Ruby Red', 'Repair Left Tarsal, Percutaneous Approach', 688.62, 1, 'https://robohash.org/consecteturrerumtotam.png?size=800x800&set=set1', 14),
(47, 'Shrimp - 31/40', 'Tomographic (Tomo) Nuclear Medicine Imaging of Upper Extremity using Iodine 131 (I-131)', 848.07, 7, 'https://robohash.org/dignissimoscupiditatequo.png?size=800x800&set=set1', 3),
(48, 'Muffin - Banana Nut Individual', 'Bypass Left Pulmonary Artery from Innominate Artery with Zooplastic Tissue, Percutaneous Endoscopic Approach', 942.32, 4, 'https://robohash.org/saepenatusculpa.png?size=800x800&set=set1', 4),
(49, 'Ice Cream - Life Savers', 'Replacement of Toe Nail with Nonautologous Tissue Substitute, External Approach', 469.16, 8529, 'https://robohash.org/undeplaceatquos.png?size=800x800&set=set1', 1),
(50, 'Gatorade - Lemon Lime', 'Insertion of Intraluminal Device into Right Basilic Vein, Percutaneous Approach', 326.58, 93088, 'https://robohash.org/idexercitationemquia.png?size=800x800&set=set1', 8),
(51, 'Doilies - 8, Paper', 'Dilation of Cystic Duct with Intraluminal Device, Percutaneous Endoscopic Approach', 945.82, 9, 'https://robohash.org/sitblanditiisaut.png?size=800x800&set=set1', 1),
(52, 'Veal - Ground', 'Insertion of Infusion Device into Left Metatarsal-Phalangeal Joint, Percutaneous Endoscopic Approach', 124.68, 836, 'https://robohash.org/liberominusipsam.png?size=800x800&set=set1', 19),
(53, 'Coffee Swiss Choc Almond', 'Revision of Nonautologous Tissue Substitute in Diaphragm, Via Natural or Artificial Opening', 351.70, 56, 'https://robohash.org/nihilquoaut.png?size=800x800&set=set1', 11),
(54, 'Bagels Poppyseed', 'Drainage of Left Eye, External Approach, Diagnostic', 823.72, 1, 'https://robohash.org/laudantiumetminima.png?size=800x800&set=set1', 4),
(55, 'Ginger - Ground', 'Destruction of Left Tympanic Membrane, Percutaneous Endoscopic Approach', 872.10, 86, 'https://robohash.org/deseruntnonmagni.png?size=800x800&set=set1', 6),
(56, 'Sambuca Cream', 'Release Left Thorax Muscle, Percutaneous Endoscopic Approach', 396.05, 4, 'https://robohash.org/dolorumcupiditaterem.png?size=800x800&set=set1', 14),
(57, 'Bread - Dark Rye', 'Replacement of Right Lacrimal Bone with Nonautologous Tissue Substitute, Open Approach', 424.82, 256, 'https://robohash.org/ainciduntquae.png?size=800x800&set=set1', 10),
(58, 'Longos - Cheese Tortellini', 'Destruction of Right Lower Arm and Wrist Muscle, Percutaneous Endoscopic Approach', 304.19, 5, 'https://robohash.org/quiquiadolores.png?size=800x800&set=set1', 16),
(59, 'Syrup - Monin, Swiss Choclate', 'Supplement Right Radial Artery with Synthetic Substitute, Open Approach', 539.56, 33, 'https://robohash.org/hicvoluptatumperspiciatis.png?size=800x800&set=set1', 20),
(60, 'Pastry - Banana Muffin - Mini', 'Tomographic (Tomo) Nuclear Medicine Imaging of Skull and Cervical Spine using Other Radionuclide', 786.45, 8248, 'https://robohash.org/accusantiumquaeratqui.png?size=800x800&set=set1', 20),
(61, 'Prunes - Pitted', 'Introduction of Destructive Agent into Respiratory Tract, Percutaneous Approach', 376.30, 82838, 'https://robohash.org/autvoluptasomnis.png?size=800x800&set=set1', 14),
(62, 'Table Cloth 54x54 White', 'Drainage of Right Upper Arm Muscle with Drainage Device, Percutaneous Endoscopic Approach', 444.42, 84853, 'https://robohash.org/eosetaut.png?size=800x800&set=set1', 15),
(63, 'Rambutan', 'Revision of Interbody Fusion Device in Lumbosacral Joint, External Approach', 440.22, 23950, 'https://robohash.org/maximequiaenim.png?size=800x800&set=set1', 8),
(64, 'Bread - Pumpernickel', 'Change Other Device on Right Toe', 992.38, 286, 'https://robohash.org/autvoluptatemsint.png?size=800x800&set=set1', 14),
(65, 'Coffee Cup 12oz 5342cd', 'Bypass Left Common Iliac Artery to Bilateral External Iliac Arteries, Open Approach', 655.27, 5, 'https://robohash.org/aliquidtemporequisquam.png?size=800x800&set=set1', 9),
(66, 'Cookie Trail Mix', 'Reposition Right Foot Vein, Percutaneous Approach', 145.83, 30094, 'https://robohash.org/pariaturautemaut.png?size=800x800&set=set1', 10),
(67, 'Higashimaru Usukuchi Soy', 'Destruction of Right Upper Leg Skin, External Approach', 606.33, 498, 'https://robohash.org/eumnostrumcum.png?size=800x800&set=set1', 18),
(68, 'Goat - Leg', 'Control Bleeding in Mediastinum, Percutaneous Approach', 496.46, 85870, 'https://robohash.org/rerumidarchitecto.png?size=800x800&set=set1', 20),
(69, 'Pop Shoppe Cream Soda', 'Removal of Synthetic Substitute from Right Eye, Percutaneous Approach', 871.72, 156, 'https://robohash.org/voluptatedistinctiovoluptatem.png?size=800x800&set=set1', 2),
(70, 'Sugar - Palm', 'Plain Radiography of Right Pulmonary Vein using Low Osmolar Contrast', 160.21, 13, 'https://robohash.org/etquibusdamiste.png?size=800x800&set=set1', 5),
(71, 'Squash - Butternut', 'Bypass Right External Iliac Artery to Bilateral Femoral Arteries with Autologous Arterial Tissue, Percutaneous Endoscopic Approach', 138.19, 194, 'https://robohash.org/ipsaquoearum.png?size=800x800&set=set1', 3),
(72, 'Onions - Cooking', 'Fusion of Right Hip Joint, Percutaneous Approach', 223.81, 748, 'https://robohash.org/utvoluptatumut.png?size=800x800&set=set1', 7),
(73, 'Tea - Lemon Green Tea', 'Revision of Synthetic Substitute in Left Pelvic Bone, External Approach', 533.05, 36, 'https://robohash.org/laborumeosomnis.png?size=800x800&set=set1', 2),
(74, 'Beef - Bones, Cut - Up', 'Repair Left Mandible, Percutaneous Approach', 117.61, 22612, 'https://robohash.org/iureettotam.png?size=800x800&set=set1', 20),
(75, 'Longos - Grilled Chicken With', 'Drainage of Scrotum with Drainage Device, Percutaneous Approach', 854.97, 0, 'https://robohash.org/inventoreiustoaspernatur.png?size=800x800&set=set1', 14),
(76, 'Foil - 4oz Custard Cup', 'Inspection of Left Elbow Joint, Percutaneous Approach', 867.89, 5190, 'https://robohash.org/eaqueomnisquia.png?size=800x800&set=set1', 19),
(77, 'Nut - Hazelnut, Whole', 'Supplement Lumbosacral Disc with Autologous Tissue Substitute, Open Approach', 485.40, 51, 'https://robohash.org/voluptasquaeratveritatis.png?size=800x800&set=set1', 15),
(78, 'Sausage - Breakfast', 'Drainage of Tibial Nerve, Percutaneous Endoscopic Approach, Diagnostic', 330.75, 29673, 'https://robohash.org/etauteminventore.png?size=800x800&set=set1', 15),
(79, 'Eggplant Oriental', 'Extirpation of Matter from Right Pelvic Bone, Open Approach', 719.24, 2670, 'https://robohash.org/voluptascumquetempora.png?size=800x800&set=set1', 11),
(80, 'Galliano', 'Introduction of Antiarrhythmic into Central Artery, Percutaneous Approach', 545.58, 81, 'https://robohash.org/voluptatemmagnamnihil.png?size=800x800&set=set1', 10),
(81, 'Brandy Cherry - Mcguinness', 'Reposition Coccyx, Percutaneous Approach', 946.73, 1, 'https://robohash.org/nullainearum.png?size=800x800&set=set1', 10),
(82, 'Stainless Steel Cleaner Vision', 'Supplement Hymen with Autologous Tissue Substitute, Via Natural or Artificial Opening', 360.46, 0, 'https://robohash.org/quiaipsamet.png?size=800x800&set=set1', 8),
(83, 'Longos - Grilled Chicken With', 'Drainage of Head and Neck Tendon, Open Approach, Diagnostic', 938.90, 3, 'https://robohash.org/etsuntpariatur.png?size=800x800&set=set1', 9),
(84, 'Lotus Root', 'Excision of Retroperitoneum, Percutaneous Approach', 738.11, 5158, 'https://robohash.org/earumvoluptateipsa.png?size=800x800&set=set1', 17),
(85, 'Soup Campbells Beef With Veg', 'Bypass Right Brachial Vein to Upper Vein with Autologous Venous Tissue, Percutaneous Endoscopic Approach', 352.51, 2, 'https://robohash.org/temporeatqueut.png?size=800x800&set=set1', 17),
(86, 'Beef - Montreal Smoked Brisket', 'Repair Right Index Finger, Percutaneous Approach', 436.88, 39464, 'https://robohash.org/praesentiumquisvelit.png?size=800x800&set=set1', 13),
(87, 'Long Island Ice Tea', 'Supplement of Posterior Neck Subcutaneous Tissue and Fascia with Autologous Tissue Substitute, Open Approach', 467.20, 5, 'https://robohash.org/etullamvoluptates.png?size=800x800&set=set1', 13),
(88, 'Nut - Almond, Blanched, Ground', 'Replacement of Right Tibia with Nonautologous Tissue Substitute, Percutaneous Endoscopic Approach', 755.18, 67, 'https://robohash.org/saepeperspiciatissuscipit.png?size=800x800&set=set1', 10),
(89, 'Figs', 'Drainage of Dura Mater with Drainage Device, Open Approach', 864.57, 47, 'https://robohash.org/tenetursitaut.png?size=800x800&set=set1', 12),
(90, 'Lidsoupcont Rp12dn', 'Dilation of Left Common Carotid Artery, Bifurcation, with Three Drug-eluting Intraluminal Devices, Open Approach', 375.00, 84603, 'https://robohash.org/estillumquam.png?size=800x800&set=set1', 14),
(91, 'Soup - French Onion', 'Insertion of Contraceptive Device into Left Lower Leg Subcutaneous Tissue and Fascia, Open Approach', 247.60, 2673, 'https://robohash.org/nesciuntautemet.png?size=800x800&set=set1', 17),
(92, 'Pastry - Cherry Danish - Mini', 'Excision of Bilateral Vas Deferens, Percutaneous Approach', 653.22, 85977, 'https://robohash.org/numquamquosunt.png?size=800x800&set=set1', 20),
(93, 'Carbonated Water - Blackcherry', 'Replacement of Right Finger Phalanx with Autologous Tissue Substitute, Open Approach', 152.17, 23358, 'https://robohash.org/estquamodio.png?size=800x800&set=set1', 1),
(94, 'Foil Wrap', 'Supplement Left Wrist Joint with Nonautologous Tissue Substitute, Percutaneous Endoscopic Approach', 268.10, 7105, 'https://robohash.org/temporaquiex.png?size=800x800&set=set1', 4),
(95, 'Tahini Paste', 'Removal of Nonautologous Tissue Substitute from Upper Bursa and Ligament, Open Approach', 495.78, 28, 'https://robohash.org/easuscipitnisi.png?size=800x800&set=set1', 6),
(96, 'Chick Peas - Dried', 'Destruction of Right Elbow Bursa and Ligament, Open Approach', 891.29, 7, 'https://robohash.org/repellatnondistinctio.png?size=800x800&set=set1', 5),
(97, 'Glucose', 'Inspection of Heart, Percutaneous Endoscopic Approach', 277.14, 21, 'https://robohash.org/excepturiquamquibusdam.png?size=800x800&set=set1', 3),
(98, 'Water - Evian 355 Ml', 'Computerized Tomography (CT Scan) of Right Femur', 100.81, 457, 'https://robohash.org/quisullamculpa.png?size=800x800&set=set1', 7),
(99, 'Coffee - Cafe Moreno', 'Removal of Autologous Tissue Substitute from Coccygeal Joint, Open Approach', 171.15, 0, 'https://robohash.org/voluptatemarchitectoexcepturi.png?size=800x800&set=set1', 9),
(100, 'V8 Splash Strawberry Kiwi', 'Replacement of Right Lower Eyelid with Autologous Tissue Substitute, External Approach', 631.26, 772, 'https://robohash.org/distinctioautprovident.png?size=800x800&set=set1', 6);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detalle_pedidos`
--
ALTER TABLE `detalle_pedidos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_pedido` (`id_pedido`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_categoria` (`id_categoria`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalle_pedidos`
--
ALTER TABLE `detalle_pedidos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_pedidos`
--
ALTER TABLE `detalle_pedidos`
  ADD CONSTRAINT `detalle_pedidos_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
