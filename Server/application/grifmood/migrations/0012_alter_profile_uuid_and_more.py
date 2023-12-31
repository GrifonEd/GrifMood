# Generated by Django 4.1.4 on 2023-05-22 16:29

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('grifmood', '0011_alter_profile_id_alter_profile_uuid'),
    ]

    operations = [
        migrations.AlterField(
            model_name='profile',
            name='uuid',
            field=models.CharField(default='e1d6edc8-f893-11ed-a2ee-bdfdb077e062', max_length=500, verbose_name='uuid'),
        ),
        migrations.AlterUniqueTogether(
            name='connectionprofile',
            unique_together={('profileWatching', 'profileShare')},
        ),
    ]
