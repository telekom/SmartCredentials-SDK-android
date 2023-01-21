<!DOCTYPE html>

<html>
	<head>
		<title>EMAIL CLIENT</title>
		<style>
			span
			{
				display: inline-block;
				vertical-align: middle;
				line-height: normal;
				color:#FFFFFF
			}
			div 
			{
				height: 50px;
				text-align: center;
			}
		</style>
	</head>
	<body bgcolor="#e20074">
		<h1>
			<div>
				<span>
					<?php
						$alert = $_GET['alert'];
						if ($alert == "1")
						{
							echo "sent email to user";
						}
					?>
				</span>
			</div>
		</h1>
	</body>
</html>
