<?php
	require_once('lib/pdf/tcpdf.php'); require_once('lib/phplib/simplexml.class.php'); 
	$student_name = ''; $mode = ''; $cer_date = '';
	
	if(count($_POST)>0) { $student_name = $_POST['student_name']; $mode = $_POST['certificate_mode'];  $cer_date = $_POST['certificate_date']; }
	
	if($mode!='') {
	
		if($cer_date=='') $cer_date = Date("d F, Y",time());
	
		$pdf = new TCPDF('L', PDF_UNIT, PDF_PAGE_FORMAT, true, 'UTF-8', false);
		
		// set document information
		$pdf->SetCreator(PDF_CREATOR);
		$pdf->SetAuthor('Nobel Biocare');
		$pdf->SetTitle('Assessment Certificate');
		$pdf->SetSubject('Assessment Certificate');
		$pdf->SetKeywords('Assessment Certificate');
			
		$pdf->setPrintHeader(false);
		$pdf->setPrintFooter(false);
		
		// set default monospaced font
		$pdf->SetDefaultMonospacedFont(PDF_FONT_MONOSPACED);
		
		//set margins
		$pdf->SetMargins(0, 0, 0);
		
		//set auto page breaks
		$pdf->SetAutoPageBreak(TRUE, 10);
		
		//set image scale factor
		$pdf->setImageScale(PDF_IMAGE_SCALE_RATIO);
		
		//set some language-dependent strings
		//$pdf->setLanguageArray($l);
		
		// ---------------------------------------------------------
		
		// set font
		//$pdf->SetFont('dejavusans', '', 10);
		
		// add a page
		$pdf->AddPage('P',array('910px','800px'));
		
		$xml = simplexml_load_file("certificate_en.xml"); $xml_data = array();
		
		foreach($xml->children() as $child) {
			$xml_data[$child->getName()] = $child;
		}
		
		
		// create some HTML content
		$html = '
			<table align="center" cellpadding="0" cellspacing="0" border="0">
			<tr valign="top"><td align="center" colspan="3">&nbsp;</td></tr>
			<tr valign="top"><td style="width:20px;">&nbsp;</td><td align="right" style="width:710px;"><br><img src="'.$xml_data['logo'].'" /></td><td style="width:20px;">&nbsp;</td></tr>
			<tr valign="top"><td align="left" colspan="3" >&nbsp;</td></tr>
			<tr valign="top">
				<td style="width:80px;">&nbsp;</td><td align="left" style="width:580px;"><font style="font-size:120px; font-weight:700; color:#6a6a6a">'.$xml_data['title'].'</font></td><td style="width:80px;">&nbsp;</td>
			</tr>
			<tr valign="top"><td align="left" colspan="3" style="width:100%;border-top:solid 1px #cccccc"><br><br></td></tr>
			<tr valign="top">
				<td style="width:80px;">&nbsp;</td><td align="left" style="width:580px;"><font style="font-size:50px;color:#6a6a6a">'.$xml_data['wishMessage'].'</font></td><td style="width:80px;">&nbsp;</td>
			</tr>
			<tr valign="top">
				<td style="width:80px;">&nbsp;</td><td align="left" style="width:580px;"><font style="font-size:80px;color:#000; height:100px;">'.str_replace("#name#",$student_name,$xml_data['studentName']).'</font></td><td style="width:80px;">&nbsp;</td>
			</tr>
			
			<tr valign="top">
				<td style="width:80px;">&nbsp;</td><td align="left" style="width:580px;"><font style="font-size:50px;color:#6a6a6a;">'.str_replace("#date#",$cer_date,$xml_data['dateMessage']).'</font></td><td style="width:80px;">&nbsp;</td>
			</tr>			
			<tr valign="top"><td align="left" colspan="3" ><br><br><br></td></tr>			
			<tr valign="top"><td align="left" colspan="3" style="width:100%"><img src="'.$xml_data['bgImage'].'"></td></tr>			
			<tr valign="top"><td align="left" colspan="3" ><br><br></td></tr>		
			<tr valign="top">
				<td style="width:80px;">&nbsp;</td><td align="left" style="width:580px;"><font style="font-size:50px;color:#6a6a6a">'.$xml_data['footGreet'].'</font></td><td style="width:80px;">&nbsp;</td>
			</tr>
			<tr valign="top">
				<td style="width:80px;">&nbsp;</td><td align="left" style="width:580px;"><font style="font-size:50px;color:#6a6a6a">'.$xml_data['signature'].'</font></td><td style="width:80px;">&nbsp;</td>
			</tr>
			<tr valign="top">
				<td style="width:80px;">&nbsp;</td><td align="left" style="width:580px;"><font style="font-size:50px;color:#6a6a6a">'.$xml_data['designation'].'</font></td><td style="width:80px;">&nbsp;</td>
			</tr>
			<tr valign="top">
				<td style="width:80px;">&nbsp;</td><td align="left" style="width:580px;"><font style="font-size:50px;color:#6a6a6a">'.$xml_data['department'].'</font></td><td style="width:80px;">&nbsp;</td>
			</tr>
			</table>
		';
		// output the HTML content
		$pdf->writeHTML($html, true, false, true, false, '');
		$pdf->lastPage();
		
		// ---------------------------------------------------------
		//Close and output PDF document
		if($mode==1)
			$pdf->Output('Assessment_Certificate.pdf', 'I');
		else
			$pdf->Output('Assessment_Certificate.pdf', 'D');
	}	
?>