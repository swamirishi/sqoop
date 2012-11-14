/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sqoop.client.shell;

import java.util.List;

import org.apache.sqoop.client.core.ClientError;
import org.apache.sqoop.common.SqoopException;
import org.codehaus.groovy.tools.shell.Shell;

public class SetCommand extends SqoopCommand
{
  private SetServerFunction serverFunction;
  private SetOptionFunction optionFunction;

  protected SetCommand(Shell shell) {
    super(shell, "set", "\\st",
        new String[] {"server", "option"},
        "Set", "info");
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Object execute(List args) {
    if (args.size() == 0) {
      io.out.println("Usage: set " + getUsage());
      io.out.println();
      return null;
    }
    resolveVariables(args);
    String func = (String)args.get(0);
    if (func.equals("server")) {
      if (serverFunction == null) {
        serverFunction = new SetServerFunction(io);
      }
      return serverFunction.execute(args);

    } else if (func.equals("option")) {
      if (optionFunction == null) {
        optionFunction = new SetOptionFunction(io);
      }
      return optionFunction.execute(args);

    } else {
      String msg = "Usage: set " + getUsage();
      throw new SqoopException(ClientError.CLIENT_0002, msg);
    }
  }
}
