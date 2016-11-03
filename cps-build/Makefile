showdeb:
	#dpkg -c target/md380codeplug-tool_*.deb
	dpkg -x target/md380codeplug-tool_*.deb target/migtool-deb
	(cd target/migtool-deb; ar -x ../md380codeplug-tool_*.deb; tar xvfz control.tar.gz)
