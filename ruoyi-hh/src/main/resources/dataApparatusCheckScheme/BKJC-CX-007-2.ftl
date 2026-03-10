<html>



<body lang=ZH-CN style='text-justify-trim:punctuation'>

<div class=WordSection1 style='layout-grid:15.6pt'>

<table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none'>
 <tr style='height:35.8pt'>
  <td width=189 valign=top style='width:50.2pt;border:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>抽采单元名称</span></p>
  </td>
  <td width=169 valign=top style='width:50.2pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>工作面名称</span></p>
  </td>
  <td width=139 valign=top style='width:50.2pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>混合流量</span></p>
  </td>
  <td width=122 valign=top style='width:50.2pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>纯流量</span></p>
  </td>
  <td width=141 valign=top style='width:50.3pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>温度</span></p>
  </td>
  <td width=168 valign=top style='width:50.3pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>甲烷浓度</span></p>
  </td>
  <td width=161 valign=top style='width:50.3pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>一氧化碳浓度</span></p>
  </td>
  <td width=166 valign=top style='width:50.3pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>抽采负压</span></p>
  </td>
  <td width=168 valign=top style='width:50.3pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>二氧化碳浓度</span></p>
  </td>
  <td width=164 valign=top style='width:50.3pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>累计抽采混合量</span></p>
  </td>
  <td width=147 valign=top style='width:50.3pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>累计抽采纯量</span></p>
  </td>
  <td width=112 valign=top style='width:50.3pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>乙烯浓度</span></p>
  </td>
  <td width=111 valign=top style='width:50.3pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>乙炔浓度</span></p>
  </td>
  <td width=156 valign=top style='width:50.3pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:35.8pt'>
  <p class=MsoNormal><span style='font-family:宋体'>氧气浓度</span></p>
  </td>
 </tr>
 <#list schemeList as scheme>
 <tr style='height:14.25pt'>
  <td width=189 valign=top style='width:50.2pt;border:solid windowtext 1.0pt;
  border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.measuringpointname!""}</span></p>
  </td>
  <td width=169 valign=top style='width:50.2pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.workingfacename!""}</span></p>
  </td>
  <td width=139 valign=top style='width:50.2pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.mixtureflow!""}</span></p>
  </td>
  <td width=122 valign=top style='width:50.2pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.pureflow!""}</span></p>
  </td>
  <td width=141 valign=top style='width:50.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.temperature!""}</span></p>
  </td>
  <td width=168 valign=top style='width:50.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.ch4concentration!""}</span></p>
  </td>
  <td width=161 valign=top style='width:50.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.coconcentration!""}</span></p>
  </td>
  <td width=166 valign=top style='width:50.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.negativepressure!""}</span></p>
  </td>
  <td width=168 valign=top style='width:50.3pt;border-top:none;border-left:              
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.co2concentration!""}</span></p>
  </td>
  <td width=164 valign=top style='width:50.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.totalmixtureflow!""}</span></p>
  </td>
  <td width=147 valign=top style='width:50.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.totalpureflow!""}</span></p>
  </td>
  <td width=112 valign=top style='width:50.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.ethene!""}</span></p>
  </td>
  <td width=111 valign=top style='width:50.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.ethyne!""}</span></p>
  </td>
  <td width=156 valign=top style='width:50.3pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:14.25pt'>
  <p class=MsoNormal><span lang=EN-US>${scheme.oconcentration!""}</span></p>
  </td>
 </tr>
  </#list>
</table>

<p class=MsoNormal><span lang=EN-US>&nbsp;</span></p>

</div>

</body>

</html>
