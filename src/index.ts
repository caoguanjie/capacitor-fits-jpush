import { registerPlugin } from '@capacitor/core';

import type { FitsPushPlugin } from './definitions';

const FitsPush = registerPlugin<FitsPushPlugin>('FitsPush', {
  web: () => import('./web').then(m => new m.FitsPushWeb()),
});

export * from './definitions';
export { FitsPush };
