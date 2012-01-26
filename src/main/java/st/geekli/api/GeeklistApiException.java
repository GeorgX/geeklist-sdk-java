/*
 * Copyright (C) 2012 Stefan Hoth, Sebastian Mauer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package st.geekli.api;

public class GeeklistApiException extends Exception {

	private static final long serialVersionUID = 8498810365927383069L;
	private int code = -1;
	
	public GeeklistApiException(Exception e) {
		super(e);
	}
	
	public GeeklistApiException(String message)
	{
		super(message);
	}
	
	public GeeklistApiException(String message, int code)
	{
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
