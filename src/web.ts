import { WebPlugin } from '@capacitor/core';

import type { FitsPushPlugin } from './definitions';

export class FitsPushWeb extends WebPlugin implements FitsPushPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
